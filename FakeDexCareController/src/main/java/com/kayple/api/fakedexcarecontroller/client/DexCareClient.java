package com.kayple.api.fakedexcarecontroller.client;

import com.kayple.api.fakedexcarecontroller.client.dto.DeviceListResponse;
import com.kayple.api.fakedexcarecontroller.client.dto.ReadingPageResponse;
import com.kayple.api.fakedexcarecontroller.config.DexCareProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClient;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

// Step 2: call the DexCare data endpoints. Handles pages, token refresh and retries.
@Slf4j
@Component
@RequiredArgsConstructor
public class DexCareClient {

    private static final int MAX_ATTEMPTS = 4;              // 1 try + 3 retries
    private static final long FIRST_BACKOFF_MS = 1_000;     // 1s, 2s, 4s
    private static final long MAX_RETRY_AFTER_SECONDS = 30;

    private final RestClient dexCareRestClient;
    private final DexCareTokenClient tokenClient;
    private final DexCareProperties props;

    public List<DeviceListResponse.DexCareDevice> getDevices() {
        DeviceListResponse response = callWithRetry("GET /v1/devices", () ->
                dexCareRestClient.get()
                        .uri("/v1/devices")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenClient.getAccessToken())
                        .retrieve()
                        .body(DeviceListResponse.class));
        return response == null || response.devices() == null ? List.of() : response.devices();
    }

    // Reads ALL pages from 'from' until now.
    public List<ReadingPageResponse.DexCareReading> getReadingsSince(String deviceId, Instant from) {
        List<ReadingPageResponse.DexCareReading> all = new ArrayList<>();
        int page = 0;
        int totalPages = 0;
        do {
            final int currentPage = page;
            ReadingPageResponse response = callWithRetry("GET readings " + deviceId + " page " + currentPage, () ->
                    dexCareRestClient.get()
                            .uri("/v1/devices/{deviceId}/readings?from={from}&page={page}&size={size}",
                                    deviceId, from.toString(), currentPage, props.pageSize())
                            .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenClient.getAccessToken())
                            .retrieve()
                            .body(ReadingPageResponse.class));
            if (response == null) {
                break;
            }
            if (response.records() != null) {
                all.addAll(response.records());
            }
            totalPages = response.totalPages();
            page++;
        } while (page < totalPages);
        return all;
    }

    // ---------------------------------------------------------------------
    // Retry rules (what each vendor error means for us):
    //   401        -> token problem: get a new token, try again
    //   429        -> too many calls: wait "Retry-After" seconds, try again
    //   5xx        -> vendor is broken right now: wait 1s, 2s, 4s, try again
    //   timeout    -> network problem: same as 5xx
    //   other 4xx  -> our request is wrong: do NOT retry, fail now
    // ---------------------------------------------------------------------
    private <T> T callWithRetry(String what, Supplier<T> call) {
        long backoffMs = FIRST_BACKOFF_MS;
        for (int attempt = 1; ; attempt++) {
            try {
                return call.get();
            } catch (HttpClientErrorException.Unauthorized e) {
                if (attempt >= MAX_ATTEMPTS) throw e;
                log.warn("{}: 401 from DexCare, getting a new token (attempt {})", what, attempt);
                tokenClient.invalidate();
            } catch (HttpClientErrorException.TooManyRequests e) {
                if (attempt >= MAX_ATTEMPTS) throw e;
                long waitSeconds = retryAfterSeconds(e);
                log.warn("{}: 429 rate limited, waiting {}s (attempt {})", what, waitSeconds, attempt);
                sleep(waitSeconds * 1_000);
            } catch (HttpServerErrorException | ResourceAccessException e) {
                if (attempt >= MAX_ATTEMPTS) throw e;
                log.warn("{}: {} , retry in {} ms (attempt {})", what, e.getMessage(), backoffMs, attempt);
                sleep(backoffMs);
                backoffMs *= 2;
            }
        }
    }

    private long retryAfterSeconds(HttpClientErrorException e) {
        try {
            String header = e.getResponseHeaders() == null ? null : e.getResponseHeaders().getFirst("Retry-After");
            long seconds = header == null ? 5 : Long.parseLong(header.trim());
            return Math.min(seconds, MAX_RETRY_AFTER_SECONDS);
        } catch (NumberFormatException ex) {
            return 5;
        }
    }

    private void sleep(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("Interrupted while waiting to retry", ie);
        }
    }
}

package com.kayple.api.fakedexcarecontroller.client;

import com.kayple.api.fakedexcarecontroller.config.DexCareProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

import java.time.Instant;
import java.util.Map;

// Step 1: get an access token (OAuth2 client credentials) and REUSE it until it expires.
@Slf4j
@Component
@RequiredArgsConstructor
public class DexCareTokenClient {

    private static final long REFRESH_SECONDS_BEFORE_EXPIRY = 30;   // refresh a bit early

    private final RestClient dexCareRestClient;
    private final DexCareProperties props;

    private String accessToken;
    private Instant expiresAt = Instant.EPOCH;

    // synchronized: two threads must not fetch two tokens at the same time
    public synchronized String getAccessToken() {
        boolean expiredSoon = Instant.now().isAfter(expiresAt.minusSeconds(REFRESH_SECONDS_BEFORE_EXPIRY));
        if (accessToken == null || expiredSoon) {
            fetchNewToken();
        }
        return accessToken;
    }

    // Called when DexCare answers 401: forget the token so the next call gets a new one.
    public synchronized void invalidate() {
        accessToken = null;
    }

    private void fetchNewToken() {
        // Form data must be a MultiValueMap for application/x-www-form-urlencoded
        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        form.add("grant_type", "client_credentials");
        form.add("client_id", props.clientId());
        form.add("client_secret", props.clientSecret());

        Map<String, Object> response = dexCareRestClient.post()
                .uri("/oauth/token")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(form)
                .retrieve()
                .body(new ParameterizedTypeReference<Map<String, Object>>() {});

        if (response == null || !response.containsKey("access_token")) {
            throw new IllegalStateException("Failed to retrieve access token from DexCare");
        }
        accessToken = response.get("access_token").toString();
        long expiresIn = Long.parseLong(response.getOrDefault("expires_in", 300).toString());
        expiresAt = Instant.now().plusSeconds(expiresIn);
        log.info("Got new DexCare token, valid for {} seconds", expiresIn);
    }
}

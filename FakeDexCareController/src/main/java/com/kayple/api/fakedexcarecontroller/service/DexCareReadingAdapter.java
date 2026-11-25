package com.kayple.api.fakedexcarecontroller.service;

import com.kayple.api.fakedexcarecontroller.client.dto.ReadingPageResponse.DexCareReading;
import com.kayple.api.fakedexcarecontroller.domain.GlucoseReading;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.format.DateTimeParseException;
import java.util.Optional;

// Step 3: ADAPTER — turns the vendor's format into OUR entity.
// If we add a second vendor (iSense), it gets its own adapter; the rest of the app doesn't change.
@Component
public class DexCareReadingAdapter {

    private static final int MIN_MG_DL = 20;
    private static final int MAX_MG_DL = 600;

    // Empty = skip this record (sensor error, missing value, bad time, impossible value).
    public Optional<GlucoseReading> toEntity(String deviceId, DexCareReading r) {
        if (!"ok".equals(r.status()) || r.value() == null) {
            return Optional.empty();
        }
        if (r.value() < MIN_MG_DL || r.value() > MAX_MG_DL) {
            return Optional.empty();
        }
        Instant measuredAt = parseTime(r.systemTime());
        if (measuredAt == null) {
            return Optional.empty();
        }
        return Optional.of(new GlucoseReading(r.recordId(), deviceId, measuredAt, r.value(), r.trend()));
    }

    public Instant parseTime(String text) {
        try {
            return text == null ? null : Instant.parse(text);
        } catch (DateTimeParseException e) {
            return null;
        }
    }
}

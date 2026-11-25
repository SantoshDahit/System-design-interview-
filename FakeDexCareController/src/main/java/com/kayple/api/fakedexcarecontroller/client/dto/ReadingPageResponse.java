package com.kayple.api.fakedexcarecontroller.client.dto;

import java.util.List;

// Shape of DexCare's  GET /v1/devices/{id}/readings  response (one page).
public record ReadingPageResponse(
        String deviceId,
        String unit,
        int page,
        int size,
        int totalElements,
        int totalPages,
        List<DexCareReading> records
) {

    public record DexCareReading(
            String recordId,
            String systemTime,     // UTC text like "2026-11-24T00:10:00Z" (we convert it in the adapter)
            Integer value,         // can be null when status = sensorError
            String trend,
            String status
    ) {
    }
}

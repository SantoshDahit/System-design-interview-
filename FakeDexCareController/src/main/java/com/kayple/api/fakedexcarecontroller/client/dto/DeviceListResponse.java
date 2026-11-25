package com.kayple.api.fakedexcarecontroller.client.dto;

import java.util.List;

// Shape of DexCare's  GET /v1/devices  response.
public record DeviceListResponse(List<DexCareDevice> devices) {

    public record DexCareDevice(
            String deviceId,
            String patientRef,
            String model,
            String unit,
            int intervalMinutes
    ) {
    }
}

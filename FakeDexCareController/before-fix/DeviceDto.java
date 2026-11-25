package com.kayple.api.fakedexcarecontroller;

import lombok.Getter;

public record DeviceDto() {

    public record CreateDeviceDto(
            String deviceId,
            String patientRef,
            String model,
            String unit,
            int intervalMinutes
    ){

    }

    @Getter
    public static class ResponseDeviceDto {
        String deviceId;
        String patientRef;
        String model;
        String unit;
        int intervalMinutes;


        public ResponseDeviceDto(Device device) {
            this.deviceId = device.getDeviceId();
            this.patientRef = device.getPatientRef();
            this.model = device.getModel();
            this.unit = device.getUnit();
            this.intervalMinutes = device.getIntervalMinutes();
        }
    }

}

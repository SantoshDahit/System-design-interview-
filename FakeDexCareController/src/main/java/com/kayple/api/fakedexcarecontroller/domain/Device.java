package com.kayple.api.fakedexcarecontroller.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;

// OUR copy of a vendor device.
@Entity
@Table(name = "device")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)   // JPA needs it, others should use the constructor
public class Device {

    @Id                                // the vendor gives us the ID (DX-1001), so no @GeneratedValue
    private String deviceId;

    private String patientRef;
    private String model;
    private String unit;
    private int intervalMinutes;

    // Incremental sync: the next sync asks only for data after this time.
    private Instant lastSyncedAt;

    public Device(String deviceId, String patientRef, String model, String unit, int intervalMinutes) {
        this.deviceId = deviceId;
        updateInfo(patientRef, model, unit, intervalMinutes);
    }

    public void updateInfo(String patientRef, String model, String unit, int intervalMinutes) {
        this.patientRef = patientRef;
        this.model = model;
        this.unit = unit;
        this.intervalMinutes = intervalMinutes;
    }

    public void markSyncedUntil(Instant time) {
        this.lastSyncedAt = time;
    }
}

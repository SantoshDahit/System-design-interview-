package com.kayple.api.fakedexcarecontroller.web;

import com.kayple.api.fakedexcarecontroller.domain.Device;
import com.kayple.api.fakedexcarecontroller.domain.GlucoseReading;
import com.kayple.api.fakedexcarecontroller.repository.DeviceRepository;
import com.kayple.api.fakedexcarecontroller.repository.GlucoseReadingRepository;
import com.kayple.api.fakedexcarecontroller.service.DexCareSyncService;
import com.kayple.api.fakedexcarecontroller.service.SyncResult;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

// OUR API (port 8082) to trigger a sync and look at what we saved.
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class SyncController {

    private final DexCareSyncService syncService;
    private final DeviceRepository deviceRepository;
    private final GlucoseReadingRepository readingRepository;

    // Run a sync now (no need to wait for the scheduler).
    @PostMapping("/sync")
    public SyncResult syncNow() {
        return syncService.syncAll();
    }

    @GetMapping("/devices")
    public List<DeviceView> devices() {
        return deviceRepository.findAll().stream()
                .map(d -> new DeviceView(d.getDeviceId(), d.getPatientRef(), d.getModel(),
                        d.getLastSyncedAt(), readingRepository.countByDeviceId(d.getDeviceId())))
                .toList();
    }

    // Newest 50 readings of one device.
    @GetMapping("/devices/{deviceId}/readings")
    public List<ReadingView> readings(@PathVariable String deviceId) {
        return readingRepository.findTop50ByDeviceIdOrderByMeasuredAtDesc(deviceId).stream()
                .map(r -> new ReadingView(r.getRecordId(), r.getMeasuredAt(), r.getValueMgDl(), r.getTrend()))
                .toList();
    }

    public record DeviceView(String deviceId, String patientRef, String model, Instant lastSyncedAt, long savedReadings) {}

    public record ReadingView(String recordId, Instant measuredAt, int mgDl, String trend) {}
}

package com.kayple.api.fakedexcarecontroller.service;

import com.kayple.api.fakedexcarecontroller.client.DexCareClient;
import com.kayple.api.fakedexcarecontroller.client.dto.DeviceListResponse.DexCareDevice;
import com.kayple.api.fakedexcarecontroller.client.dto.ReadingPageResponse.DexCareReading;
import com.kayple.api.fakedexcarecontroller.config.DexCareProperties;
import com.kayple.api.fakedexcarecontroller.domain.Device;
import com.kayple.api.fakedexcarecontroller.domain.GlucoseReading;
import com.kayple.api.fakedexcarecontroller.repository.DeviceRepository;
import com.kayple.api.fakedexcarecontroller.repository.GlucoseReadingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;

// Step 4: the sync flow  —  devices -> readings since lastSyncedAt -> adapter -> save new ones only.
@Slf4j
@Service
@RequiredArgsConstructor
public class DexCareSyncService {

    private final DexCareClient dexCareClient;
    private final DexCareReadingAdapter adapter;
    private final DeviceRepository deviceRepository;
    private final GlucoseReadingRepository readingRepository;
    private final DexCareProperties props;

    // synchronized: if the scheduler and POST /api/sync run together, they take turns.
    public synchronized SyncResult syncAll() {
        List<DexCareDevice> vendorDevices = dexCareClient.getDevices();

        int fetched = 0, saved = 0, duplicates = 0, skipped = 0;
        List<String> failed = new ArrayList<>();

        for (DexCareDevice vendorDevice : vendorDevices) {
            try {
                Device device = upsertDevice(vendorDevice);
                int[] counts = syncReadings(device);     // {fetched, saved, duplicates, skipped}
                fetched += counts[0];
                saved += counts[1];
                duplicates += counts[2];
                skipped += counts[3];
            } catch (Exception e) {
                // One broken device must not stop the others. Its lastSyncedAt doesn't move,
                // so the next run fetches the same data again -> nothing is lost.
                log.error("Sync failed for device {}: {}", vendorDevice.deviceId(), e.getMessage());
                failed.add(vendorDevice.deviceId());
            }
        }

        SyncResult result = new SyncResult(vendorDevices.size(), fetched, saved, duplicates, skipped, failed);
        log.info("DexCare sync finished: {}", result);
        return result;
    }

    private Device upsertDevice(DexCareDevice d) {
        Device device = deviceRepository.findById(d.deviceId())
                .orElseGet(() -> new Device(d.deviceId(), d.patientRef(), d.model(), d.unit(), d.intervalMinutes()));
        device.updateInfo(d.patientRef(), d.model(), d.unit(), d.intervalMinutes());
        return deviceRepository.save(device);
    }

    private int[] syncReadings(Device device) {
        Instant from = device.getLastSyncedAt() != null
                ? device.getLastSyncedAt()
                : Instant.parse(props.initialFrom());

        List<DexCareReading> records = dexCareClient.getReadingsSince(device.getDeviceId(), from);

        // Convert with the adapter. A Map by recordId also removes duplicates inside the same response.
        Map<String, GlucoseReading> converted = new LinkedHashMap<>();
        int skipped = 0;
        for (DexCareReading r : records) {
            Optional<GlucoseReading> entity = adapter.toEntity(device.getDeviceId(), r);
            if (entity.isPresent()) {
                converted.putIfAbsent(r.recordId(), entity.get());
            } else {
                skipped++;
            }
        }

        // Idempotent save: only keep IDs that are not in our DB yet.
        Set<String> alreadySaved = new HashSet<>();
        readingRepository.findAllById(converted.keySet()).forEach(g -> alreadySaved.add(g.getRecordId()));
        List<GlucoseReading> toSave = converted.values().stream()
                .filter(g -> !alreadySaved.contains(g.getRecordId()))
                .toList();
        readingRepository.saveAll(toSave);

        // Move lastSyncedAt forward to the newest record we received (even skipped ones),
        // ONLY after everything above worked.
        records.stream()
                .map(r -> adapter.parseTime(r.systemTime()))
                .filter(Objects::nonNull)
                .max(Comparator.naturalOrder())
                .ifPresent(device::markSyncedUntil);
        deviceRepository.save(device);

        int duplicates = converted.size() - toSave.size();
        log.info("Device {}: fetched {}, saved {}, duplicates {}, skipped {} (from {})",
                device.getDeviceId(), records.size(), toSave.size(), duplicates, skipped, from);
        return new int[]{records.size(), toSave.size(), duplicates, skipped};
    }
}

package com.kayple.api.fakedexcarecontroller.sync;

import com.kayple.api.fakedexcarecontroller.service.DexCareSyncService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

// Step 5: run the sync automatically.
// fixedDelay = wait N ms AFTER the previous run finished (runs never overlap).
// With many servers, add ShedLock so only one server runs it.
@Slf4j
@Component
@RequiredArgsConstructor
public class DexCareSyncJob {

    private final DexCareSyncService syncService;

    @Scheduled(initialDelayString = "${dexcare.sync.initial-delay-ms:5000}",
               fixedDelayString = "${dexcare.sync.interval-ms:300000}")
    public void run() {
        try {
            syncService.syncAll();
        } catch (Exception e) {
            // e.g. DexCare is down: log it, try again next time
            log.error("Scheduled DexCare sync failed: {}", e.getMessage());
        }
    }
}

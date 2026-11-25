package com.kayple.api.fakedexcarecontroller.service;

import java.util.List;

// What one sync did. Returned by POST /api/sync and logged by the job.
public record SyncResult(
        int devices,
        int fetched,          // records received from DexCare
        int saved,            // new rows written to our DB
        int duplicates,       // already in our DB -> not saved again
        int skipped,          // sensor errors / invalid values
        List<String> failedDevices
) {
}

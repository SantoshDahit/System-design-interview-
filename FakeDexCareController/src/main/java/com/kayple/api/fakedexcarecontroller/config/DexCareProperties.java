package com.kayple.api.fakedexcarecontroller.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

// All "dexcare.*" values from application.yml, in one typed object.
// Better than many @Value fields: one place, typed, easy to test.
@ConfigurationProperties(prefix = "dexcare")
public record DexCareProperties(
        String baseUrl,            // http://localhost:8081
        String clientId,           // given by the vendor
        String clientSecret,       // given by the vendor (use an env variable in real life)
        String initialFrom,        // first sync starts here, e.g. 2026-11-24T00:00:00Z
        int pageSize,              // readings per page (vendor max = 100)
        Duration connectTimeout,   // e.g. 3s
        Duration readTimeout       // e.g. 10s
) {
}

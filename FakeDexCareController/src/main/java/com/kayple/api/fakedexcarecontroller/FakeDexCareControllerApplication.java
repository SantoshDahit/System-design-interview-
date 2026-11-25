package com.kayple.api.fakedexcarecontroller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

// This app is the CLIENT (receiver): it pulls data from the DexCare vendor API
// (http://localhost:8081) and saves it in its own H2 database.
@SpringBootApplication
@EnableScheduling            // needed for @Scheduled in DexCareSyncJob
public class FakeDexCareControllerApplication {

    public static void main(String[] args) {
        SpringApplication.run(FakeDexCareControllerApplication.class, args);
    }
}

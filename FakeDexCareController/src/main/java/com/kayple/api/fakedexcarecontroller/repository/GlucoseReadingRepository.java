package com.kayple.api.fakedexcarecontroller.repository;

import com.kayple.api.fakedexcarecontroller.domain.GlucoseReading;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GlucoseReadingRepository extends JpaRepository<GlucoseReading, String> {

    List<GlucoseReading> findTop50ByDeviceIdOrderByMeasuredAtDesc(String deviceId);

    long countByDeviceId(String deviceId);
}

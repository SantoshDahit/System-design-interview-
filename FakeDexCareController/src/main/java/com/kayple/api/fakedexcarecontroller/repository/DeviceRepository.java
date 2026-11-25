package com.kayple.api.fakedexcarecontroller.repository;

import com.kayple.api.fakedexcarecontroller.domain.Device;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeviceRepository extends JpaRepository<Device, String> {
}

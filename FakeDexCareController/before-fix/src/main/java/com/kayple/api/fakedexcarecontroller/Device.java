package com.kayple.api.fakedexcarecontroller;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "device")
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Device {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String deviceId;

    private String patientRef;

    private String model;

    private String unit;

    private int intervalMinutes;


}

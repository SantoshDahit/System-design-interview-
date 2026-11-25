package com.kayple.api.fakedexcarecontroller.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;

// OUR copy of one glucose reading.
@Entity
@Table(name = "glucose_reading")   // real DB: add an index on (device_id, measured_at)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GlucoseReading {

    // The vendor's recordId is the primary key -> the same reading can never be saved twice.
    @Id
    private String recordId;

    @Column(nullable = false)
    private String deviceId;

    @Column(nullable = false)
    private Instant measuredAt;        // always UTC

    @Column(nullable = false)
    private int valueMgDl;             // not "value": that's a reserved word in H2

    private String trend;

    private Instant savedAt;           // when WE saved it (useful for debugging)

    public GlucoseReading(String recordId, String deviceId, Instant measuredAt, int valueMgDl, String trend) {
        this.recordId = recordId;
        this.deviceId = deviceId;
        this.measuredAt = measuredAt;
        this.valueMgDl = valueMgDl;
        this.trend = trend;
        this.savedAt = Instant.now();
    }
}

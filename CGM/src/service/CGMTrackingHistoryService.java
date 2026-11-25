package service;

import entity.CGMTrackingHistory;
import entity.Patient;
import exception.CGMInvalidValueException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Stores readings, grouped by patient.
public class CGMTrackingHistoryService {

    public static final long SENSOR_MIN_MG_DL = 20;
    public static final long SENSOR_MAX_MG_DL = 600;

    private final Map<String, List<CGMTrackingHistory>> readingsByPatient = new HashMap<>();
    private int nextReadingNumber = 1;                 // the system creates reading IDs

    public CGMTrackingHistory addingCgmHistory(long mgDl, LocalDateTime measuredAt, Patient patient) {
        if (mgDl < SENSOR_MIN_MG_DL || mgDl > SENSOR_MAX_MG_DL) {
            throw new CGMInvalidValueException(mgDl);
        }
        String id = "C" + nextReadingNumber++;
        CGMTrackingHistory reading = new CGMTrackingHistory(id, mgDl, measuredAt, patient);
        readingsByPatient.computeIfAbsent(patient.getPatientId(), key -> new ArrayList<>()).add(reading);
        return reading;
    }

    public List<CGMTrackingHistory> getReadings(String patientId) {
        return new ArrayList<>(readingsByPatient.getOrDefault(patientId, List.of()));
    }

    // Latest reading at or after 'since' (and not in the future). null if none.
    public CGMTrackingHistory getLatestSince(String patientId, LocalDateTime since, LocalDateTime now) {
        CGMTrackingHistory latest = null;
        for (CGMTrackingHistory reading : readingsByPatient.getOrDefault(patientId, List.of())) {
            LocalDateTime time = reading.getMeasuredAt();
            boolean inWindow = !time.isBefore(since) && !time.isAfter(now);
            if (inWindow && (latest == null || time.isAfter(latest.getMeasuredAt()))) {
                latest = reading;
            }
        }
        return latest;
    }
}

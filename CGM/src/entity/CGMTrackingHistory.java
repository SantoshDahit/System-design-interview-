package entity;

import java.time.LocalDateTime;

// One glucose reading from a sensor.
public class CGMTrackingHistory {

    private final String cgmTrackingHistoryId;
    private final long mgDl;
    private final LocalDateTime measuredAt;      // date + time, so midnight works
    private final Patient patient;

    public CGMTrackingHistory(String cgmTrackingHistoryId, long mgDl, LocalDateTime measuredAt, Patient patient) {
        this.cgmTrackingHistoryId = cgmTrackingHistoryId;
        this.mgDl = mgDl;
        this.measuredAt = measuredAt;
        this.patient = patient;
    }

    public String getCgmTrackingHistoryId() { return cgmTrackingHistoryId; }
    public long getMgDl() { return mgDl; }
    public LocalDateTime getMeasuredAt() { return measuredAt; }
    public Patient getPatient() { return patient; }

    @Override
    public String toString() {
        return cgmTrackingHistoryId + " " + patient.getPatientId() + " " + mgDl + " mg/dL at " + measuredAt.toLocalTime();
    }
}

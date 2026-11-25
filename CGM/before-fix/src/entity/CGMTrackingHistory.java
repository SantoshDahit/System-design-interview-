package entity;

import java.time.LocalTime;

public class CGMTrackingHistory {

    private String cgmTrackingHistoryId;
    private long mgDl;

    private LocalTime measuredAt;

    private Patient patient;

    public CGMTrackingHistory(String cgmTrackingHistoryId, long mgDl, LocalTime measuredAt, Patient patient){
        this.cgmTrackingHistoryId = cgmTrackingHistoryId;
        this.mgDl = mgDl;
        this.measuredAt = measuredAt;
        this.patient = patient;

    }

    @Override
    public String toString() {
        return "Patient: " + patient+ " cgmTrackingHistoryId: " + cgmTrackingHistoryId + "mgDl: " + mgDl + "messuretAt: " + measuredAt;
    }
}

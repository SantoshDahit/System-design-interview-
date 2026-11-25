package entity;

import java.time.LocalDateTime;

public class Alert {
    private final Patient patient;
    private final AlertType type;
    private final long mgDl;
    private final LocalDateTime measuredAt;

    public Alert(Patient patient, AlertType type, long mgDl, LocalDateTime measuredAt) {
        this.patient = patient;
        this.type = type;
        this.mgDl = mgDl;
        this.measuredAt = measuredAt;
    }

    public Patient getPatient() { return patient; }
    public AlertType getType() { return type; }
    public long getMgDl() { return mgDl; }
    public LocalDateTime getMeasuredAt() { return measuredAt; }

    // Format from the task: [ALERT] P1 LOW 68 mg/dL at 09:10
    public String toMessage() {
        return "[ALERT] " + patient.getPatientId() + " " + type + " " + mgDl + " mg/dL at " + measuredAt.toLocalTime();
    }
}

package service;

import entity.Alert;
import entity.AlertType;
import entity.CGMTrackingHistory;
import notifier.Notifier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AlertService {

    public static final long LOW_BELOW_MG_DL = 70;     // < 70 is LOW, 70 is normal
    public static final long HIGH_ABOVE_MG_DL = 250;   // > 250 is HIGH, 250 is normal

    private final Notifier notifier;
    private final Map<String, List<Alert>> alertHistory = new HashMap<>();

    public AlertService(Notifier notifier) {
        this.notifier = notifier;
    }

    // Returns the alert if one was sent, otherwise null.
    public Alert checkAndSend(CGMTrackingHistory reading) {
        AlertType type = decide(reading.getMgDl());
        if (type == null) {
            return null;                                   // normal value
        }
        Alert alert = new Alert(reading.getPatient(), type, reading.getMgDl(), reading.getMeasuredAt());
        notifier.send(alert);
        alertHistory.computeIfAbsent(reading.getPatient().getPatientId(), key -> new ArrayList<>()).add(alert);
        return alert;
    }

    private AlertType decide(long mgDl) {
        if (mgDl < LOW_BELOW_MG_DL) return AlertType.LOW;
        if (mgDl > HIGH_ABOVE_MG_DL) return AlertType.HIGH;
        return null;
    }

    public List<Alert> getAlerts(String patientId) {
        return new ArrayList<>(alertHistory.getOrDefault(patientId, List.of()));
    }
}

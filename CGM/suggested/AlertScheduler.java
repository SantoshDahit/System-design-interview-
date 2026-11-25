package scheduler;

import entity.Alert;
import entity.CGMTrackingHistory;
import entity.Patient;
import service.AlertService;
import service.CGMTrackingHistoryService;
import service.PatientService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

// Called every minute (by a real scheduler or @Scheduled in Spring).
public class AlertScheduler {

    public static final int WINDOW_MINUTES = 60;   // older readings are stale

    private final PatientService patientService;
    private final CGMTrackingHistoryService cgmTrackingHistoryService;
    private final AlertService alertService;

    public AlertScheduler(PatientService patientService,
                          CGMTrackingHistoryService cgmTrackingHistoryService,
                          AlertService alertService) {
        this.patientService = patientService;
        this.cgmTrackingHistoryService = cgmTrackingHistoryService;
        this.alertService = alertService;
    }

    // Fix #2: check every patient's latest reading from the last 60 minutes.
    public List<Alert> runCheck(LocalDateTime now) {
        List<Alert> sent = new ArrayList<>();
        LocalDateTime since = now.minusMinutes(WINDOW_MINUTES);

        for (Patient patient : patientService.getAllPatients()) {
            CGMTrackingHistory latest = cgmTrackingHistoryService.getLatestSince(patient.getPatientId(), since, now);
            if (latest == null) {
                continue;                                  // no fresh data -> no alert
            }
            Alert alert = alertService.checkAndSend(latest);
            if (alert != null) {
                sent.add(alert);
            }
        }
        return sent;
    }
}

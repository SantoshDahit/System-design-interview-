package scheduler;

import entity.CGMTrackingHistory;
import entity.Patient;
import service.AlertService;
import service.CGMTrackingHistoryService;
import service.PatientService;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class AlertScheduler {

    private AlertService alertService;
    private PatientService patientService;
    private CGMTrackingHistoryService cgmTrackingHistoryService;


    public void runCheck(LocalDateTime now) {
        for (Patient patient : patientService.getAll()) {
            CGMTrackingHistory latest = cgmTrackingHistoryService.latestSince(patient.getId(), now.minusMinutes(60));
            if (latest == null) continue;            // no fresh data → no alert
            alertService.check(patient, latest);     // LOW / HIGH / nothing
        }
    }
}

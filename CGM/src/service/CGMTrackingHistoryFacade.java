package service;

import entity.CGMTrackingHistory;
import entity.Patient;

import java.time.LocalDateTime;
import java.util.List;

// One simple entry point for adding and reading glucose data.
public class CGMTrackingHistoryFacade {

    private final PatientService patientService;
    private final CGMTrackingHistoryService cgmTrackingHistoryService;

    // Fix #1: the objects are passed in (plain Java "dependency injection").
    public CGMTrackingHistoryFacade(PatientService patientService,
                                    CGMTrackingHistoryService cgmTrackingHistoryService) {
        this.patientService = patientService;
        this.cgmTrackingHistoryService = cgmTrackingHistoryService;
    }

    public CGMTrackingHistory collectingCgmHistory(String patientId, long mgDl, LocalDateTime measuredAt) {
        Patient patient = patientService.getPatientById(patientId);   // throws if unknown
        CGMTrackingHistory reading = cgmTrackingHistoryService.addingCgmHistory(mgDl, measuredAt, patient);
        System.out.println("Saved reading: " + reading);
        return reading;
    }

    public List<CGMTrackingHistory> getCGMTrackingHistoryByPatientId(String patientId) {
        patientService.getPatientById(patientId);                     // throws if unknown
        return cgmTrackingHistoryService.getReadings(patientId);
    }
}

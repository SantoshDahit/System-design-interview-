package service;

import entity.CGMTrackingHistory;
import entity.Patient;
import exception.PatientInvalidException;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class CGMTrackingHistoryFacade {
    private PatientService patientService;
    private CGMTrackingHistoryService cgmTrackingHistoryService;
    private AlertService alertService;


    public CGMTrackingHistory collectingCgmHistory(String cgmTrackingHistoryId, long mgDl, LocalTime measuredAt, String patientId) {

        Patient patient = patientService.getPatientById(patientId);
        if (!patient.equals(patientService.registerPatientMap.get(patientId))) {
            throw new PatientInvalidException(patientId);
        }
        CGMTrackingHistory cgmTrackingHistory = cgmTrackingHistoryService.addingCgmHistory(cgmTrackingHistoryId, mgDl, measuredAt, patient);
        alertService.sendingAlert(mgDl, measuredAt);

        return cgmTrackingHistory;
    }


    public List<CGMTrackingHistory> getCGMTrackingHistoryByPatientId(String patientId) {
        Patient patient = patientService.getPatientById(patientId);
        List<CGMTrackingHistory> cgmTrackingHistoryList = new ArrayList<>();
        for (CGMTrackingHistory cgmTrackingHistory : cgmTrackingHistoryList) {
            cgmTrackingHistoryService. collectingHistory.getOrDefault(patient, cgmTrackingHistory);
            cgmTrackingHistoryList.add(cgmTrackingHistory);
        }
        return cgmTrackingHistoryList;
    }


}

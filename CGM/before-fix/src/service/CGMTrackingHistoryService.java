package service;

import entity.CGMTrackingHistory;
import entity.Patient;
import exception.CGMInvalidValueException;
import exception.PatientInvalidException;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CGMTrackingHistoryService {

    public Map<String, CGMTrackingHistory> collectingHistory = new HashMap<>();
    private PatientService patientService;
    private AlertService alertService;
    public CGMTrackingHistory addingCgmHistory(String cgmTrackingHistoryId, long mgDl, LocalTime measuredAt, Patient patient){

        if (mgDl > 500){
          throw new CGMInvalidValueException(mgDl);
        }
        CGMTrackingHistory cgmTrackingHistory = new CGMTrackingHistory(cgmTrackingHistoryId, mgDl, measuredAt,patient);
        collectingHistory.put(cgmTrackingHistoryId, cgmTrackingHistory);
        System.out.println(cgmTrackingHistory.toString());
        return cgmTrackingHistory;
    }

    public CGMTrackingHistory getCGMTrackingHistoryById(String CGMTrackingHistoryId){
        return collectingHistory.get(CGMTrackingHistoryId);
    }




}

package service;

import entity.CGMTrackingHistory;
import entity.Patient;
import exception.PatientInvalidException;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AlertService {

    public void sendingAlert(long receivingMgDl, LocalTime measuredAt){
        long minMgDl = 70L;
        long maxMgDl = 250L;

        if (receivingMgDl < minMgDl ) {
            String message = "your minMgDl has been less than normal value, mgDl: " + receivingMgDl + "measuredAt: " + measuredAt;
            System.out.println(message);
        }

        if (receivingMgDl > maxMgDl ) {
            String message = "your minMgDl has been higher than normal value, mgDl: " + receivingMgDl + "measuredAt: " + measuredAt;
            System.out.println(message);
        }

    }

}

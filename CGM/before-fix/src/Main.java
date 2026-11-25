import service.CGMTrackingHistoryFacade;
import service.PatientService;

import java.time.LocalTime;

public class Main {
    public static void main(String[] args) {

        PatientService patientService = new PatientService();
        CGMTrackingHistoryFacade cgmTrackingHistoryFacade = new CGMTrackingHistoryFacade();

        LocalTime now = LocalTime.of(9,0);

        System.out.println("--- Registering patient---");
        patientService.registerPatient("P1", "Minji");
        patientService.registerPatient("P2", "Junho");
        patientService.registerPatient("P3", "Sora");
        patientService.registerPatient("P4", "Hyun");

        System.out.println("\n--- Start reading the mgDl value ---");
        System.out.println("\n--- for patient P1 ---");

        cgmTrackingHistoryFacade.collectingCgmHistory("C1", 120L, now, "P1");
        cgmTrackingHistoryFacade.collectingCgmHistory("C2", 95L,  now.plusMinutes(10), "P1");
        cgmTrackingHistoryFacade.collectingCgmHistory("C3", 68L,  now.plusMinutes(20), "P1");


        System.out.println("\n--- for patient P2 ---");

        cgmTrackingHistoryFacade.collectingCgmHistory("C4", 180L, now, "P2");
        cgmTrackingHistoryFacade.collectingCgmHistory("C5", 262L,  now.plusMinutes(8), "P2");

        System.out.println("\n--- for patient P2 ---");

        cgmTrackingHistoryFacade.collectingCgmHistory("C6", 180L, now.minusHours(2), "P2");

        System.out.println("\n -- printing cgmList of P1");
        cgmTrackingHistoryFacade.getCGMTrackingHistoryByPatientId("P1");

    }
}

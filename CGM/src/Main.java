import entity.Alert;
import exception.CGMException;
import notifier.ConsoleNotifier;
import scheduler.AlertScheduler;
import service.AlertService;
import service.CGMTrackingHistoryFacade;
import service.CGMTrackingHistoryService;
import service.PatientService;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {

        // ---- Wiring: create each object ONCE and pass the same objects around.
        //      (In Spring Boot, the container does this part for you.)
        PatientService patientService = new PatientService();
        CGMTrackingHistoryService readingService = new CGMTrackingHistoryService();
        AlertService alertService = new AlertService(new ConsoleNotifier());
        CGMTrackingHistoryFacade facade = new CGMTrackingHistoryFacade(patientService, readingService);
        AlertScheduler scheduler = new AlertScheduler(patientService, readingService, alertService);

        LocalDate today = LocalDate.of(2026, 11, 26);

        System.out.println("--- Registering patients ---");
        patientService.registerPatient("P1", "Minji");
        patientService.registerPatient("P2", "Junho");
        patientService.registerPatient("P3", "Sora");
        patientService.registerPatient("P4", "Hyun");

        System.out.println("\n--- Readings ---");
        facade.collectingCgmHistory("P1", 120, at(today, 9, 0));
        facade.collectingCgmHistory("P1", 95, at(today, 9, 5));
        facade.collectingCgmHistory("P1", 68, at(today, 9, 10));
        facade.collectingCgmHistory("P2", 180, at(today, 9, 0));
        facade.collectingCgmHistory("P2", 262, at(today, 9, 8));
        facade.collectingCgmHistory("P3", 60, at(today, 7, 30));   // stale at 09:11
        facade.collectingCgmHistory("P4", 110, at(today, 9, 2));

        System.out.println("\n--- Invalid input must be rejected ---");
        tryReading(facade, "P9", 100, at(today, 9, 0));            // unknown patient
        tryReading(facade, "P1", 700, at(today, 9, 0));            // out of sensor range

        System.out.println("\n--- runCheck(09:11) ---");
        scheduler.runCheck(at(today, 9, 11));

        System.out.println("\n--- Alert history ---");
        for (String id : new String[]{"P1", "P2", "P3", "P4"}) {
            System.out.println(id + ": " + alertService.getAlerts(id).size() + " alert(s)");
        }
    }

    private static void tryReading(CGMTrackingHistoryFacade facade, String patientId, long mgDl, LocalDateTime time) {
        try {
            facade.collectingCgmHistory(patientId, mgDl, time);
        } catch (CGMException e) {                                 // parent catches all our errors
            System.out.println("Rejected: " + e.getMessage());
        }
    }

    private static LocalDateTime at(LocalDate day, int hour, int minute) {
        return day.atTime(hour, minute);
    }
}

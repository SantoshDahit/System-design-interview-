import entity.Alert;
import entity.AlertType;
import exception.CGMInvalidValueException;
import exception.PatientInvalidException;
import notifier.Notifier;
import org.junit.jupiter.api.BeforeEach;
import scheduler.AlertScheduler;
import service.AlertService;
import service.CGMTrackingHistoryFacade;
import service.CGMTrackingHistoryService;
import service.PatientService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;



class AlertSchedulerTest {

    private static final LocalDateTime NOW = LocalDateTime.of(2026, 11, 26, 9, 11);

    private final List<Alert> sentAlerts = new ArrayList<>();   // fake notifier saves alerts here
    private CGMTrackingHistoryFacade facade;
    private AlertScheduler scheduler;
    private AlertService alertService;

    @BeforeEach
    void setUp() {
        PatientService patientService = new PatientService();
        CGMTrackingHistoryService readingService = new CGMTrackingHistoryService();
        Notifier fakeNotifier = sentAlerts::add;                // no console, easy to check
        alertService = new AlertService(fakeNotifier);
        facade = new CGMTrackingHistoryFacade(patientService, readingService);
        scheduler = new AlertScheduler(patientService, readingService, alertService);

        patientService.registerPatient("P1", "Minji");
    }

    @Test
    void lowReadingSendsLowAlert() {
        facade.collectingCgmHistory("P1", 68, NOW.minusMinutes(1));

        scheduler.runCheck(NOW);

        assertEquals(1, sentAlerts.size());
        assertEquals(AlertType.LOW, sentAlerts.get(0).getType());
        assertEquals(1, alertService.getAlerts("P1").size());
    }

    @Test
    void highReadingSendsHighAlert() {
        facade.collectingCgmHistory("P1", 262, NOW.minusMinutes(3));

        scheduler.runCheck(NOW);

        assertEquals(AlertType.HIGH, sentAlerts.get(0).getType());
    }

    @Test
    void onlyTheLatestReadingCounts() {
        facade.collectingCgmHistory("P1", 60, NOW.minusMinutes(20));   // was low
        facade.collectingCgmHistory("P1", 110, NOW.minusMinutes(5));   // now normal

        scheduler.runCheck(NOW);

        assertTrue(sentAlerts.isEmpty());
    }

    @Test
    void staleReadingOlderThan60MinutesIsIgnored() {
        facade.collectingCgmHistory("P1", 60, NOW.minusMinutes(61));

        scheduler.runCheck(NOW);

        assertTrue(sentAlerts.isEmpty());
    }

    @Test
    void exactly70And250AreNormal() {
        facade.collectingCgmHistory("P1", 70, NOW.minusMinutes(2));
        scheduler.runCheck(NOW);
        facade.collectingCgmHistory("P1", 250, NOW.minusMinutes(1));
        scheduler.runCheck(NOW);

        assertTrue(sentAlerts.isEmpty());
    }

    @Test
    void unknownPatientIsRejected() {
        assertThrows(PatientInvalidException.class,
                () -> facade.collectingCgmHistory("P9", 100, NOW));
    }

    @Test
    void outOfRangeValueIsRejected() {
        assertThrows(CGMInvalidValueException.class, () -> facade.collectingCgmHistory("P1", 700, NOW));
        assertThrows(CGMInvalidValueException.class, () -> facade.collectingCgmHistory("P1", 10, NOW));
    }
}

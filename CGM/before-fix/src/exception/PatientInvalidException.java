package exception;

public class PatientInvalidException extends CGMException {
    public PatientInvalidException(String patientId) {
        super("Invalid patientId, please check: " + patientId);
    }
}

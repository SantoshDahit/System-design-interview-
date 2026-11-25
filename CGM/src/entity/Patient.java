package entity;

public class Patient {
    private final String patientId;
    private final String name;

    public Patient(String patientId, String name) {
        this.patientId = patientId;
        this.name = name;
    }

    public String getPatientId() {
        return patientId;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "patientId: " + patientId + " name: " + name;
    }
}

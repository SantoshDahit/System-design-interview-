package entity;

public class Patient {
    private String patientId;
    private String name;

    public Patient(String patientId, String name){
        this.patientId = patientId;
        this.name = name;
    }

    @Override
    public String toString() {
        return "patientId: " +  patientId + " name: " + name;
    }
}

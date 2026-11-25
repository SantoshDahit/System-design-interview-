package service;

import entity.Patient;
import exception.PatientInvalidException;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class PatientService {

    private final Map<String, Patient> registerPatientMap = new LinkedHashMap<>();   // keeps insert order

    public Patient registerPatient(String patientId, String name) {
        Patient patient = new Patient(patientId, name);
        registerPatientMap.put(patientId, patient);
        System.out.println(patient);
        return patient;
    }

    // Throws if the patient doesn't exist (no more null checks everywhere).
    public Patient getPatientById(String patientId) {
        Patient patient = registerPatientMap.get(patientId);
        if (patient == null) {
            throw new PatientInvalidException(patientId);
        }
        return patient;
    }

    public List<Patient> getAllPatients() {
        return new ArrayList<>(registerPatientMap.values());
    }
}

package service;

import entity.Patient;

import java.util.HashMap;
import java.util.Map;

public class PatientService {

    public Map<String, Patient> registerPatientMap = new HashMap<>();
    public Patient registerPatient(String patientId, String name){
        Patient patient = new Patient(patientId, name);
        registerPatientMap.put(patientId, patient);
        System.out.println(patient.toString());
        return patient;
    }

    public Patient getPatientById(String patientId){
        return registerPatientMap.get(patientId);
    }

}

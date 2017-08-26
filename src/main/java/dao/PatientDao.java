package dao;

import models.Treatment;
import models.Patient;

import java.util.List;

public interface PatientDao {
    //create
    void add(Patient patient);

    void addPatientToTreatment(Patient patient, Treatment treatment);
//
//    //read
    List<Patient> getAll();
//
    List<Treatment> getAllTreatmentsForAPatient(int patientId);


    Patient findById(int id);
//
//    //update patient info
    void update(int id, String type, String diagnosis, String name, int age);
//
////   //delete individual patient
    void deletePatientById(int id);
//
//    //delete all patients
    void clearAllPatients();
}
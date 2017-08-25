package dao;

import models.Patient;

import java.util.List;

public interface PatientDao {
    //create
    void add(Patient patient);

//    void addPatientToCancer(Patient patient, Cancer cancer);
//
//    //read
    List<Patient> getAll();
//
//    List<Cancer> getAllCancersForAPatient(int patientId);
//
//
    Patient findById(int id);
//
//    //update patient info
    void update(int id, String type, String name, int age, String diagnosis);
//
////   //delete individual patient
////    void deleteById(int id); //K
//
//    //delete all patients
//    void clearAllPatients();
}
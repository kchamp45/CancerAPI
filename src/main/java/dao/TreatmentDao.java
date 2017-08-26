package dao;

import models.Treatment;
import models.Patient;

import java.util.List;

public interface TreatmentDao {

    void add(Treatment treatment);

    void addTreatmentToPatient(Treatment treatment, Patient patient);
//
//    //read
    //find individual treatment
    Treatment findById(int id);
//    //
    List<Treatment> getAll();
//    //
    List<Patient> getAllPatientsForATreatment(int id);
//
//    //update treatment info
    void update(int id, String name, String description);
//
//    //delete all treatments
    void clearAllTreatments();

//    //delete individual treatment
    void deleteTreatmentById(int id);
}

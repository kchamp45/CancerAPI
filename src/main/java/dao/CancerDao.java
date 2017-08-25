package dao;

import models.Cancer;
import models.Patient;

import java.util.List;

public interface CancerDao {

    void add(Cancer cancer);

    void addCancerToPatient(Cancer cancer, Patient patient);
//
//    //read
    //find individual cancer
    Cancer findById(int id);
//    //
    List<Cancer> getAll();
//    //
    List<Patient> getAllPatientsForACancer(int id);
//
//    //update cancer info
    void update(int id, String name, String description);
//
//    //delete all cancers
    void clearAllCancers();

//    //delete individual cancer
    void deleteCancerById(int id);
}

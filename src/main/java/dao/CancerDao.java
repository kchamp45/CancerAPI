package dao;

import models.Cancer;
import models.Patient;

import java.util.List;

public interface CancerDao {

    void add(Cancer cancer);

    List<Cancer> getAll();

    List<Patient>getAllPatientsByCancer(int cancerId);
//
    Cancer findById(int id);

    void update(int id, String type, String description);
//
    void deleteCancerById(int id);

}

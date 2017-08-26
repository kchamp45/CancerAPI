package dao;

import models.Patient;
import models.PatientA;

import java.util.List;

public interface PatientADao {

    List<PatientA> getAll(String type);

    PatientA findById(int id);
    void updatePatientA(int id, String type, String diagnosis, String name, int age, String geneMutation, String famHistory);

    void deletePatientA(int id);

}

package dao;

import models.Patient;
import models.PatientA;

import java.util.List;

public interface PatientADao {

    List<PatientA> getAll(String type);

    PatientA findById(int id);

    void updatePatientA(int id, String gender, String diagnosis, String name, int age, String geneMutation);

    void deletePatientA(int id);

}

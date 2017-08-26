package dao;

import models.Treatment;
import models.Patient;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.ArrayList;
import java.util.List;

public class Sql2oPatientDao implements PatientDao {
    public final Sql2o sql2o;

    public Sql2oPatientDao(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public void add(Patient patient) {
        String sql = "INSERT INTO patients (gender, diagnosis, name, age, cancerId) VALUES (:gender, :diagnosis, :name, :age, :cancerId)";

        try (Connection con = sql2o.open()) {
            int id = (int) con.createQuery(sql)
                    .addParameter("gender", patient.getGender())
                    .addParameter("diagnosis", patient.getDiagnosis())
                    .addParameter("name", patient.getName())
                    .addParameter("age", patient.getAge())
                    .addParameter("cancerId", patient.getCancerId())
                    .addColumnMapping("GENDER", "gender")
                    .addColumnMapping("DIAGNOSIS", "diagnosis")
                    .addColumnMapping("NAME", "name")
                    .addColumnMapping("AGE", "age")
                    .addColumnMapping("CANCERID", "cancerId")
                    .executeUpdate()
                    .getKey();
            patient.setId(id);
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public Patient findById(int id) {
        try (Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM patients WHERE id = :id")
                    .addParameter("id", id)
                    .throwOnMappingFailure(false) //asking it to ignore empty columns
                    .executeAndFetchFirst(Patient.class);
        }
    }
    @Override
    public List<Patient> getAll() {
        try (Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM patients")
                    .throwOnMappingFailure(false)
                    .executeAndFetch(Patient.class);
        }
    }
    @Override
    public void update(int id, String newGender, String newDiagnosis, String newName, int newAge) {
        String sql = "UPDATE patients SET (gender, diagnosis, name, age) = (:gender, :diagnosis, :name, :age) WHERE id = :id";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("id", id)
                    .addParameter("gender", newGender)
                    .addParameter("diagnosis", newDiagnosis)
                    .addParameter("name", newName)
                    .addParameter("age", newAge)
                    .throwOnMappingFailure(false)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void deletePatientById(int id){
        String sql = "DELETE from patients WHERE id = :id";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("id", id)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void clearAllPatients() {
        String sql = "DELETE from patients";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }
    @Override
    public void addPatientToTreatment(Patient patient, Treatment treatment) {
        String sql = "INSERT INTO medPlans (patientId, treatmentId) VALUES (:patientId, :treatmentId)";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("patientId", patient.getId())
                    .addParameter("treatmentId", treatment.getId())
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }
    @Override
    public List<Treatment> getAllTreatmentsForAPatient(int patientId) {
        ArrayList<Treatment> treatments = new ArrayList<>(); // initialize an empty list
        String joinQuery = "SELECT treatmentid FROM medPlans WHERE patientid = :patientId";

        try (Connection con = sql2o.open()) {
            List<Integer> allTreatmentsIds = con.createQuery(joinQuery)
                    .addParameter("patientId", patientId)
                    .executeAndFetch(Integer.class);
            for (Integer treatmentId : allTreatmentsIds) {
                String treatmentQuery = "SELECT * FROM treatments WHERE id = :treatmentId";
                treatments.add(
                        con.createQuery(treatmentQuery)
                                .addParameter("treatmentId", treatmentId)
                                .executeAndFetchFirst(Treatment.class));
            }
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
        return treatments;
    }

    }

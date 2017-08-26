package dao;

import models.Cancer;
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
        String sql = "INSERT INTO patients (type, diagnosis) VALUES (:type, :diagnosis)";

        try (Connection con = sql2o.open()) {
            int id = (int) con.createQuery(sql)
                    .addParameter("type", patient.getType())
                    .addParameter("diagnosis", patient.getDiagnosis())
                    .addColumnMapping("TYPE", "type")
                    .addColumnMapping("DIAGNOSIS", "diagnosis")
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
    public void update(int id, String newType, String newDiagnosis) {
        String sql = "UPDATE patients SET (type, diagnosis) = (:type, :diagnosis) WHERE id = :id";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("id", id)
                    .addParameter("type", newType)
                    .addParameter("diagnosis", newDiagnosis)
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
    public void addPatientToCancer(Patient patient, Cancer cancer) {
        String sql = "INSERT INTO patients_cancers (patientId, cancerId) VALUES (:patientId, :cancerId)";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("patientId", patient.getId())
                    .addParameter("cancerId", cancer.getId())
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }
    @Override
    public List<Cancer> getAllCancersForAPatient(int patientId) {
        ArrayList<Cancer> cancers = new ArrayList<>(); // initialize an empty list
        String joinQuery = "SELECT cancerid FROM patients_cancers WHERE patientid = :patientId";

        try (Connection con = sql2o.open()) {
            List<Integer> allCancersIds = con.createQuery(joinQuery)
                    .addParameter("patientId", patientId)
                    .executeAndFetch(Integer.class);
            for (Integer cancerId : allCancersIds) {
                String cancerQuery = "SELECT * FROM cancers WHERE id = :cancerId";
                cancers.add(
                        con.createQuery(cancerQuery)
                                .addParameter("cancerId", cancerId)
                                .executeAndFetchFirst(Cancer.class));
            }
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
        return cancers;
    }
}

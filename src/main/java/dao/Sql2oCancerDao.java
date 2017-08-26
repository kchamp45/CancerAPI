package dao;

import models.Cancer;
import models.Patient;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.ArrayList;
import java.util.List;

public class Sql2oCancerDao implements CancerDao {
    private final Sql2o sql2o;

    public Sql2oCancerDao(Sql2o sql2o) {this.sql2o = sql2o; }

    @Override
    public void add(Cancer cancer) {
        String sql = "INSERT INTO cancers (name, description) VALUES (:name, :description)";
        try (Connection con = sql2o.open()) {
            int id = (int) con.createQuery(sql)
                    .addParameter("name", cancer.getName())
                    .addParameter("description", cancer.getDescription())
                    .addColumnMapping("NAME", "name")
                    .addColumnMapping("DESCRIPTION", "description")
                    .executeUpdate()
                    .getKey();
            cancer.setId(id);
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }
    @Override
    public Cancer findById(int id) {
        try (Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM cancers WHERE id = :id")
                    .addParameter("id", id)
                    .executeAndFetchFirst(Cancer.class);
        }
    }
    @Override
    public List<Cancer> getAll() {
        try (Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM cancers")
                    .executeAndFetch(Cancer.class);
        }
    }

    @Override
    public void update(int id, String newName, String newDescription) {
        String sql = "UPDATE cancers SET (name, description) = (:name, :description) WHERE id = :id";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("id", id)
                    .addParameter("name", newName)
                    .addParameter("description", newDescription)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }
    @Override
    public void deleteCancerById(int id) {
        String sql = "DELETE from cancers WHERE id = :id";
        String deleteJoin = "DELETE from patients_cancers WHERE cancerid = :cancerId";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("id", id)
                    .executeUpdate();
            con.createQuery(deleteJoin)
                    .addParameter("cancerId", id)
                    .executeUpdate();

        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }
    @Override
    public void clearAllCancers() {
        String sql = "DELETE from cancers";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }
    @Override
    public void addCancerToPatient(Cancer cancer, Patient patient){

        try (Connection con = sql2o.open()) {
            String sql = "INSERT INTO patients_cancers (patientid, cancerid) VALUES (:patientId, :cancerId)";
            con.createQuery(sql)
                    .addParameter("patientId", patient.getId())
                    .addParameter("cancerId", cancer.getId())
                    .throwOnMappingFailure(false)
                    .executeUpdate();
        } catch (Sql2oException ex){
            System.out.println(ex);
        }
    }

    @Override
    public List<Patient> getAllPatientsForACancer(int cancerId) {
        List<Patient> patients = new ArrayList<>();
        String joinQuery = "SELECT patientId FROM patients_cancers WHERE cancerId = :cancerId";//pull out patientids for join table when they match a cancerId in the same table

        try (Connection con = sql2o.open()) {
            List<Integer> allPatientIds = con.createQuery(joinQuery)
                    .addParameter("cancerId", cancerId)
                    .throwOnMappingFailure(false)
                    .executeAndFetch(Integer.class);
            for (Integer patientId : allPatientIds){
                String patientQuery = "SELECT * FROM patients WHERE id = :patientId";
                patients.add(
                        con.createQuery(patientQuery)
                                .addParameter("patientId", patientId) //add patientId to sql query for our search
                                .throwOnMappingFailure(false)
                                .executeAndFetchFirst(Patient.class));//patients (objects) pulled out will be from the patient class
            }
        } catch (Sql2oException ex){
            System.out.println(ex);
        }
        return patients; //return the values found from above evaluation
    }

}

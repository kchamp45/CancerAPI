package dao;

import models.Treatment;
import models.Patient;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.ArrayList;
import java.util.List;

public class Sql2oTreatmentDao implements TreatmentDao {
    private final Sql2o sql2o;

    public Sql2oTreatmentDao(Sql2o sql2o) {this.sql2o = sql2o; }

    @Override
    public void add(Treatment treatment) {
        String sql = "INSERT INTO treatments (description, duration) VALUES (:description, :duration)";
        try (Connection con = sql2o.open()) {
            int id = (int) con.createQuery(sql)
                    .addParameter("description", treatment.getDescription())
                    .addParameter("duration", treatment.getDuration())
                    .addColumnMapping("DESCRIPTION", "description")
                    .addColumnMapping("DURATION", "duration")
                    .executeUpdate()
                    .getKey();
            treatment.setId(id);
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }
    @Override
    public Treatment findById(int id) {
        try (Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM treatments WHERE id = :id")
                    .addParameter("id", id)
                    .executeAndFetchFirst(Treatment.class);
        }
    }
    @Override
    public List<Treatment> getAll() {
        try (Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM treatments")
                    .executeAndFetch(Treatment.class);
        }
    }

    @Override
    public void update(int id, String newDescription, String newDuration) {
        String sql = "UPDATE treatments SET (description, duration) = (:description, :duration) WHERE id = :id";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("id", id)
                    .addParameter("description", newDescription)
                    .addParameter("duration", newDuration)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }
    @Override
    public void deleteTreatmentById(int id) {
        String sql = "DELETE from treatments WHERE id = :id";
        String deleteJoin = "DELETE from medPlans WHERE treatmentid = :treatmentId";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("id", id)
                    .executeUpdate();
            con.createQuery(deleteJoin)
                    .addParameter("treatmentId", id)
                    .executeUpdate();

        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }
    @Override
    public void clearAllTreatments() {
        String sql = "DELETE from treatments";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }
    @Override
    public void addTreatmentToPatient(Treatment treatment, Patient patient){

        try (Connection con = sql2o.open()) {
            String sql = "INSERT INTO medPlans (patientid, treatmentid) VALUES (:patientId, :treatmentId)";
            con.createQuery(sql)
                    .addParameter("patientId", patient.getId())
                    .addParameter("treatmentId", treatment.getId())
                    .throwOnMappingFailure(false)
                    .executeUpdate();
        } catch (Sql2oException ex){
            System.out.println(ex);
        }
    }

    @Override
    public List<Patient> getAllPatientsForATreatment(int treatmentId) {
        List<Patient> patients = new ArrayList<>();
        String joinQuery = "SELECT patientId FROM medPlans WHERE treatmentId = :treatmentId";//pull out patientids for join table when they match a treatmentId in the same table

        try (Connection con = sql2o.open()) {
            List<Integer> allPatientIds = con.createQuery(joinQuery)
                    .addParameter("treatmentId", treatmentId)
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

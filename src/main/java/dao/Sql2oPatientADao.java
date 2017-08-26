package dao;

import models.PatientA;
import models.Patient;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.List;

public class Sql2oPatientADao extends Sql2oPatientDao implements PatientDao {

    public Sql2oPatientADao(Sql2o sql2o) {
        super(sql2o);
    }

    public void add(PatientA patientA) {
        String sql = "UPDATE patients SET(geneMutation) = (:geneMutation) WHERE id = :id";
        try (Connection con = sql2o.open()) {
            super.add(patientA);
            int id = patientA.getId();
            con.createQuery(sql)
                    .addParameter("id", id)
                    .addParameter("geneMutation", patientA.getGeneMutation())
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }
    public PatientA findById(int id) {
        try (Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM patients WHERE id = :id")
                    .addParameter("id", id)
                    .throwOnMappingFailure(false)
                    .executeAndFetchFirst(PatientA.class);
        }
    }
    public List getAll() {
        try (Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM patients WHERE gender= gender")
                    .throwOnMappingFailure(false)
                    .executeAndFetch(PatientA.class);
        }
    }
//
    public void updatePatientA(int id, String gender, String diagnosis, String name, int age, String geneMutation) {
        String sql = "UPDATE patients SET (geneMutation) = (:geneMutation) WHERE id = :id";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("id", id)
                    .addParameter("geneMutation", geneMutation)
                    .throwOnMappingFailure(false)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    public void deletePatientA(int id) {
        String sql = "DELETE from patients WHERE id=:id";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("id", id)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    }
package dao;

import models.Patient;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.List;

public class Sql2oPatientDao implements PatientDao {
    public final Sql2o sql2o;

    public Sql2oPatientDao(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public void add(Patient patient) {
        String sql = "INSERT INTO patients (type, name, age, diagnosis) VALUES (:type, :name, :age, :diagnosis)";

        try (Connection con = sql2o.open()) {
            int id = (int) con.createQuery(sql)
                    .addParameter("type", patient.getType())
                    .addParameter("name", patient.getName())
                    .addParameter("age", patient.getAge())
                    .addParameter("diagnosis", patient.getDiagnosis())
                    .addColumnMapping("TYPE", "type")
                    .addColumnMapping("NAME", "name")
                    .addColumnMapping("AGE", "age")
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
    public void update(int id, String newType, String newName, int newAge, String newDiagnosis) {
        String sql = "UPDATE patients SET (type, name, age, diagnosis) = (:type, :name, :age, :diagnosis) WHERE id = :id";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("id", id)
                    .addParameter("type", newType)
                    .addParameter("name", newName)
                    .addParameter("age", newAge)
                    .addParameter("diagnosis", newDiagnosis)
                    .throwOnMappingFailure(false)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }
}

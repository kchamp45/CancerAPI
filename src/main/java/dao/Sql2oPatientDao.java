package dao;

import models.Patient;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

public class Sql2oPatientDao implements PatientDao{
    public final Sql2o sql2o;

    public Sql2oPatientDao(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public void add(Patient patient){
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
}

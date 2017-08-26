package dao;

import models.Cancer;
import models.Patient;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.List;

public class Sql2oCancerDao implements CancerDao {
    private final Sql2o sql2o;

    public Sql2oCancerDao(Sql2o sql2o) {this.sql2o = sql2o;}

    @Override
    public void add(Cancer cancer) {
        String sql = "INSERT INTO cancers (type, description) VALUES (:type, :description)";
        try (Connection con = sql2o.open()) {
            int id = (int) con.createQuery(sql)
                    .addParameter("type", cancer.getType())
                    .addParameter("description", cancer.getDescription())
                    .addColumnMapping("TYPE", "type")
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
        try(Connection con = sql2o.open()){
            return con.createQuery("SELECT * FROM cancers WHERE id = :id")
                    .addParameter("id", id)
                    .executeAndFetchFirst(Cancer.class);
        }
    }

    @Override
    public List<Cancer> getAll() {
        try(Connection con = sql2o.open()){
            return con.createQuery("SELECT * FROM cancers")
                    .executeAndFetch(Cancer.class);
        }
    }

    @Override
    public void deleteCancerById(int id) {
        String sql = "DELETE from cancers WHERE id = :id";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("id", id)
                    .executeUpdate();
        } catch (Sql2oException ex){
            System.out.println(ex);
        }
    }
    @Override
    public void update(int id, String newType, String newDescription) {
        String sql = "UPDATE cancers SET (type, description) = (:type, :description) WHERE id = :id";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("id", id)
                    .addParameter("type", newType)
                    .addParameter("description", newDescription)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }
    @Override
    public List<Patient> getAllPatientsByCancer(int cancerId) {
        try (Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM patients WHERE cancerId = :cancerId")
                    .addParameter("cancerId", cancerId)
                    .executeAndFetch(Patient.class); //fetch a list
        }
    }
}

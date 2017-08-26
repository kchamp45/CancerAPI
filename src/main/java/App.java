import com.google.gson.Gson;
import dao.Sql2oCancerDao;
import dao.Sql2oPatientADao;
import dao.Sql2oPatientDao;
import exceptions.ApiException;
import models.Cancer;
import models.Patient;
import models.PatientA;
import org.sql2o.Sql2o;

import java.util.HashMap;
import java.util.Map;

import static spark.Spark.*;

public class App {

    public static void main(String[] args) {
        Sql2oCancerDao cancerDao;
        Sql2oPatientDao patientDao;
        Sql2oPatientADao patientADao;
        org.sql2o.Connection conn;
        Gson gson = new Gson();

        String connectionString = "jdbc:h2:~/jadle.db;INIT=RUNSCRIPT from 'classpath:db/create.sql'"; //check me!
        Sql2o sql2o = new Sql2o(connectionString, "", "");
        patientDao = new Sql2oPatientDao(sql2o);
        cancerDao = new Sql2oCancerDao(sql2o);
        patientADao = new Sql2oPatientADao(sql2o);
        conn = sql2o.open();

        //CREATE
        post("/patients/new", "application/json", (req, res) -> {
            Patient patient = gson.fromJson(req.body(), Patient.class);
            patientDao.add(patient);
            res.status(201);;
            return gson.toJson(patient);
        });
        get("/patients", "application/json", (req, res) -> {
            Patient patient = gson.fromJson(req.body(), Patient.class);
            res.status(201);;
            return gson.toJson(patientDao.getAll());
        });
//
        get("/patients/:id", "application/json", (req, res) -> {
            int patientId = Integer.parseInt(req.params("id"));
            return gson.toJson(patientDao.findById(patientId));
        });

        post("/patientA/new", "application/json", (req, res) -> {
            PatientA patientA = gson.fromJson(req.body(), PatientA.class);
            patientADao.add(patientA);
            res.status(201);
            return gson.toJson(patientA);
        });

        //READ
        get("/patientA", "application/json", (req, res) -> {
            PatientA patientA = gson.fromJson(req.body(), PatientA.class);
            res.status(201);
            return gson.toJson(patientDao.getAll());
        });
//
      post("/cancers/new", "application/json", (req, res) -> {
        Cancer cancer = gson.fromJson(req.body(), Cancer.class);
        cancerDao.add(cancer);
        res.status(201);;
        return gson.toJson(cancer);
    });

        get("/cancers", "application/json", (req, res) -> {
            return gson.toJson(cancerDao.getAll());
        });

        get("/cancers/:id", "application/json", (req, res) -> {
            int cancerId = Integer.parseInt(req.params("id"));
            return gson.toJson(cancerDao.findById(cancerId));
        });

        post("/patients/:patientId/patientA/new", "application/json", (req, res) -> {
            int patientId = Integer.parseInt(req.params("patientId"));
            PatientA patientA = gson.fromJson(req.body(), PatientA.class);
            patientA.setPatientId(patientId); //why do I need to set separately?
            patientADao.add(patientA);
            res.status(201);
            return gson.toJson(patientA);
        });
        //FILTERS
        exception(ApiException.class, (exception, req, res) -> {
            ApiException err = (ApiException) exception;
            Map<String, Object> jsonMap = new HashMap<>();
            jsonMap.put("status", err.getStatusCode());
            jsonMap.put("errorMessage", err.getMessage());
            res.type("application/json");
            res.status(err.getStatusCode());
            res.body(gson.toJson(jsonMap));
        });

        after((req, res) ->{
            res.type("application/json");
        });
//            post("/cancers/new", "application/json", (req, res) -> {
//                Order cancer = gson.fromJson(req.body(), Order.class);
//                cancerDao.add(cancer);
//                res.status(201);;
//                return gson.toJson(cancer);
//            });
//
//            //READ
//            get("/cancers", "application/json", (req, res) -> {
//                return gson.toJson(cancerDao.getAll());
//            });
//
//            get("/cancers/:id", "application/json", (req, res) -> {
//                int cancerId = Integer.parseInt(req.params("id"));
//                return gson.toJson(cancerDao.findById(cancerId));
//            });
//
//            post("/patients/:patientId/backpack/new", "application/json", (req, res) -> {
//                int patientId = Integer.parseInt(req.params("patientId"));
//                Backpack backpack = gson.fromJson(req.body(), Backpack.class);
//                backpack.setBackpackId(patientId); //why do I need to set separately?
//                backpackDao.add(backpack);
//                res.status(201);
//                return gson.toJson(backpack);
//            });

}

}

package dao;

import models.Patient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import static org.junit.Assert.*;

public class Sql2oPatientDaoTest {
    private Sql2oPatientDao patientDao;
//    private Sql2oCancerDao orderDao;
    private Connection conn;

    @Before
    public void setUp() throws Exception {

        String connectionString = "jdbc:h2:mem:testing;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        Sql2o sql2o = new Sql2o(connectionString, "", "");
        patientDao = new Sql2oPatientDao(sql2o);
        conn = sql2o.open();
    }

    @After
    public void tearDown() throws Exception {
        conn.close();
    }

    @Test
    public void addingCourseSetsId() throws Exception {
        Patient patient = setupNewPatient();
        int patientId = patient.getId();
        patientDao.add(patient);
        assertNotEquals(patientId, patient.getId());
    }
    public Patient setupNewPatient() { return new Patient("female", "Ann", 45, "breast cancer");
    }
}
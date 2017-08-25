package dao;

import models.Patient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import static junit.framework.TestCase.assertEquals;
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
    public Patient setupNewPatient2(){return new Patient("male", "Bob", 30, "lung cancer");
    }

    @Test
    public void canFindPatientById() throws Exception {
        Patient patient = setupNewPatient();
        patientDao.add(patient);
        Patient foundPatient = patientDao.findById(patient.getId());
        assertEquals(patient, foundPatient);
    }

    @Test
    public void returnAllAddedPatients() throws Exception {
        Patient patient = setupNewPatient();
        patientDao.add(patient);
        assertEquals(1, patientDao.getAll().size());
    }
    @Test
    public void updatePatientsInfo() throws Exception {
        String initialName = "Ann";
        Patient patient = setupNewPatient();
        patientDao.add(patient);
        patientDao.update(patient.getId(), "female", "Annette", 45, "breast cancer stage 2");
        Patient updatedPatient = patientDao.findById(patient.getId());
        assertNotEquals(initialName, updatedPatient.getName());

    }
}
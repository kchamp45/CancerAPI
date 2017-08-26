package dao;

import models.Patient;
import models.PatientA;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.*;

public class Sql2oPatientADaoTest {
    private Sql2oPatientDao patientDao;
    private Sql2oCancerDao cancerDao;
    private Sql2oPatientADao patientADao;
    private Connection conn;

    @Before
    public void setUp() throws Exception {
        String connectionString = "jdbc:h2:mem:testing;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        Sql2o sql2o = new Sql2o(connectionString, "", "");
        patientDao = new Sql2oPatientDao(sql2o);
        patientADao = new Sql2oPatientADao(sql2o);
        conn = sql2o.open();
    }

    @After
    public void tearDown() throws Exception {
        conn.close();
    }

    @Test
    public void addingCourseSetsId() throws Exception {
        PatientA patientA = setupNewPatientA();
        int patientAId = patientA.getId();
        patientADao.add(patientA);
        assertNotEquals(patientAId, patientA.getId());

    }
    @Test
    public void canFindPatientAById() throws Exception {
        PatientA patientA = setupNewPatientA();
        patientADao.add(patientA);
        PatientA foundPatientA = patientADao.findById(patientA.getId());
        assertEquals(patientA, foundPatientA);
    }

    @Test
    public void returnAllAddedPatientAs() throws Exception {
        PatientA patientA = setupNewPatientA();
        patientADao.add(patientA);
        assertEquals(1, patientADao.getAll().size());
    }
//
    @Test
    public void updatePatientAInfo() throws Exception {
        String initialAge = "30";
        PatientA patientA = setupNewPatientA();
        patientADao.add(patientA);
        patientADao.updatePatientA(patientA.getId(), "female", "breast cancer", "Ann", 35, "BRCA1/BRCA2", true);
        PatientA updatedPatientA = patientADao.findById(patientA.getId());
        assertNotEquals(initialAge, updatedPatientA.getAge());
    }
    @Test
    public void deletePatientA() throws Exception {
        PatientA patientA = setupNewPatientA();
        patientADao.add(patientA);
        patientADao.deletePatientA(patientA.getId());
        assertEquals(0, patientADao.getAll().size());
    }


    public PatientA setupNewPatientA() {
        return new PatientA("female",  "breast cancer", "Ann", 45,"BRCA1/BRCA2", true);
    }
    public Patient setupNewPatient() { return new Patient("female","breast cancer");
    }
    public Patient setupNewPatient2(){return new Patient("male", "lung cancer");
    }
}
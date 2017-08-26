package dao;

import models.Cancer;
import models.Patient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.Arrays;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.*;

public class Sql2oPatientDaoTest {
    private Sql2oPatientDao patientDao;
    private Sql2oCancerDao cancerDao;
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
    //setups for the classes
    public Patient setupNewPatient() { return new Patient("female", "breast cancer");
    }
    public Patient setupNewPatient2(){return new Patient("male","lung cancer");
    }
    public Cancer setupNewCancer(){return new Cancer("breast cancer", "cancer of the mammory tissue");
    }
    public Cancer setupNewCancer2() {
        return new Cancer("lung cancer", "cancer of the respiratory tissue");
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
        String initialType = "female";
        Patient patient = setupNewPatient();
        patientDao.add(patient);
        patientDao.update(patient.getId(), "No Gender", "breast cancer stage 2");
        Patient updatedPatient = patientDao.findById(patient.getId());
        assertNotEquals(initialType, updatedPatient.getType());

    }
    @Test
    public void deletePatientById(){
        Patient patient = setupNewPatient();
        patientDao.add(patient);
        patientDao.deletePatientById(patient.getId());
        assertEquals(0, patientDao.getAll().size());
    }

    @Test
    public void clearAllPatients(){
        Patient patient = setupNewPatient();
        Patient patient2 = setupNewPatient2();
        patientDao.add(patient);
        patientDao.add(patient2);
        int daoSize = patientDao.getAll().size();
        patientDao.clearAllPatients();
        assertTrue(daoSize > 0 && daoSize >patientDao.getAll().size());

    }

    @Test
    public void addCancer_addsPatientToCancer() throws Exception {
    Cancer testCancer = setupNewCancer();
    Cancer otherCancer = setupNewCancer2();
    cancerDao.add(testCancer);
    cancerDao.add(otherCancer);

    Patient testPatient = setupNewPatient();
    patientDao.add(testPatient);

    patientDao.addPatientToCancer(testPatient, testCancer);
    patientDao.addPatientToCancer(testPatient, otherCancer);

    assertEquals(2, patientDao.getAllCancersForAPatient(testPatient.getId()).size());
}


}
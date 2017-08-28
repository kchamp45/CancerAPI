package dao;

import models.Cancer;
import models.Patient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.*;

public class Sql2oCancerDaoTest {
    private Sql2oCancerDao cancerDao;
    private Sql2oPatientDao patientDao;
    private Connection conn;

    @Before
    public void setUp() throws Exception {
        String connectionString = "jdbc:h2:mem:testing;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        Sql2o sql2o = new Sql2o(connectionString, "", "");
        cancerDao = new Sql2oCancerDao(sql2o);
        conn = sql2o.open();
    }

    @After
    public void tearDown() throws Exception {
        conn.close();
    }

    public Cancer setupNewCancer() {return new Cancer("breast", "cancer of the mammory tissue");}
    public Patient setupNewPatient() { return new Patient("female", "stage 2", "Ann", 45, 1);
    }
    public Patient setupNewPatient2(){return new Patient("male","stage 4", "Bob", 55, 1);
    }

    @Test
    public void addingCourseSetsId() throws Exception {
        Cancer cancer = setupNewCancer();
        int origCancerId = cancer.getId();
        cancerDao.add(cancer);
        assertNotEquals(origCancerId, cancer.getId());
    }

    @Test
    public void ExistingCancerCanBeFoundById() throws Exception {
        Cancer cancer = setupNewCancer();
        cancerDao.add(cancer);
        Cancer foundCancer = cancerDao.findById(cancer.getId());
        assertEquals(cancer, foundCancer);
    }
    @Test
    public void returnAlladdedCancersFromgetAll() throws Exception {
        Cancer cancer = setupNewCancer();
        cancerDao.add(cancer);
        assertEquals(1, cancerDao.getAll().size());
    }
    @Test
    public void deleteByIdDeletesCorrectCancer() throws Exception {
        Cancer cancer = setupNewCancer();
        cancerDao.add(cancer);
        cancerDao.deleteCancerById(cancer.getId());
        assertEquals(0, cancerDao.getAll().size());
    }

    @Test
    public void updateCancersInfo() throws Exception {
        String initialType = "Breast";
        Cancer cancer = setupNewCancer();
        cancerDao.add(cancer);
        cancerDao.update(cancer.getId(),"Sarcoma", "cancer of the connective tissue");
        Cancer updatedCancer = cancerDao.findById(cancer.getId());
        assertNotEquals(initialType, updatedCancer.getType());
    }
    @Test
    public void cancerIdIsReturnedCorrectly() throws Exception {
        Patient patient = setupNewPatient();
        patientDao.add(patient);
        int originalCancerId = patient.getCancerId();
        assertEquals(originalCancerId, patientDao.findById(patient.getId()).getCancerId());
    }
//    @Test
//    public void getAllPatientsForACancer() throws Exception {
//        Cancer cancer = setupNewCancer();
//        cancerDao.add(cancer);
//        int cancerId = cancer.getId();
//
//        Patient newPatient = new Patient("female", "stage 2", "Ann", 45, cancerId);
//        Patient newPatient2 = new Patient("male","stage 4", "Bob", 55, cancerId);
//        Patient newPatient3 = new Patient("female", "stage 1", "Lucy", 60, cancerId);
//        patientDao.add(newPatient);
//        patientDao.add(newPatient2);
//
//        assertTrue(cancerDao.getAllPatientsByCancer(cancerId).size() == 2);
//        assertTrue(cancerDao.getAllPatientsByCancer(cancerId).contains(newPatient));
//        assertTrue(cancerDao.getAllPatientsByCancer(cancerId).contains(newPatient2));
//        assertFalse(cancerDao.getAllPatientsByCancer(cancerId).contains(newPatient3)); //bc no third patient added
//    }

}
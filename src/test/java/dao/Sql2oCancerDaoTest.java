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
    private Connection conn;
    private Sql2oPatientDao patientDao;

    @Before
    public void setUp() throws Exception {
        String connectionString = "jdbc:h2:mem:testing;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        Sql2o sql2o = new Sql2o(connectionString, "", "");
        cancerDao = new Sql2oCancerDao(sql2o);
        patientDao = new Sql2oPatientDao(sql2o);
        conn = sql2o.open();


    }

    @After
    public void tearDown() throws Exception {
        conn.close();
    }

    public Cancer setupNewCancer(){return new Cancer("breast cancer", "cancer of the mammory tissue");
    }
    public Cancer setupNewCancer2(){
        return new Cancer("lung cancer", "cancer of the respiratory tissue");
    }
    public Patient setupNewPatient() { return new Patient("female",  "breast cancer");
    }
    public Patient setupNewPatient2(){return new Patient("male",  "lung cancer");
    }
    @Test
    public void addingCourseSetsId() throws Exception {
        Cancer cancer = setupNewCancer();
        int origCancerId = cancer.getId();
        cancerDao.add(cancer);
        assertNotEquals(origCancerId, cancer.getId());
    }
    @Test
    public void existingCancerCanBeFoundById() throws Exception {
        Cancer cancer = setupNewCancer();
        cancerDao.add(cancer);
        Cancer foundCancer = cancerDao.findById(cancer.getId());
        assertEquals(cancer, foundCancer);
    }
    @Test
    public void addedCancersAreReturnedFromGetAll() throws Exception {
        Cancer cancer = setupNewCancer();
        cancerDao.add(cancer);
        assertEquals(1, cancerDao.getAll().size());
    }
    @Test
    public void updateCancersInfo() throws Exception {
        String initialCancerName = "sarcoma";
        Cancer cancer = setupNewCancer();
        cancerDao.add(cancer);
        cancerDao.update(cancer.getId(), "colon cancer", "cancer of the colon");
        Cancer updatedCancer = cancerDao.findById(cancer.getId());
        assertNotEquals(initialCancerName, updatedCancer.getName());
    }

    @Test
    public void deleteCancerById(){
        Cancer cancer = setupNewCancer();
        cancerDao.add(cancer);
        cancerDao.deleteCancerById(cancer.getId());
        assertEquals(0, cancerDao.getAll().size());
    }

    @Test
    public void clearAllCancers(){
        Cancer cancer = setupNewCancer();
        Cancer cancer2 = setupNewCancer2();
        cancerDao.add(cancer);
        cancerDao.add(cancer2);
        int daoSize = cancerDao.getAll().size();
        cancerDao.clearAllCancers();
        assertTrue(daoSize > 0 && daoSize >cancerDao.getAll().size());
    }
    @Test
    public void addCancer_addsCancerToPatient() throws Exception {
        Patient testPatient = setupNewPatient();
        Patient otherPatient = setupNewPatient2();
        patientDao.add(testPatient);
        patientDao.add(otherPatient);

        Cancer testCancer = setupNewCancer();
        cancerDao.add(testCancer);

        cancerDao.addCancerToPatient(testCancer, testPatient);
        cancerDao.addCancerToPatient(testCancer, otherPatient);

        assertEquals(2, cancerDao.getAllPatientsForACancer(testCancer.getId()).size());
    }
    @Test
    public void deletingCancerAlsoUpdatesJoinTable() throws Exception {
        Patient testPatient = setupNewPatient();
        patientDao.add(testPatient);

        Cancer testCancer  = setupNewCancer();
        cancerDao.add(testCancer);

        Cancer otherCancer = setupNewCancer2();
        cancerDao.add(otherCancer);

        cancerDao.addCancerToPatient(testCancer, testPatient);
        cancerDao.addCancerToPatient(otherCancer, testPatient);

        cancerDao.deleteCancerById(testCancer.getId());
        assertEquals(0, cancerDao.getAllPatientsForACancer(testCancer.getId()).size());
    }
}
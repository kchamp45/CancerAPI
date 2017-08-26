package dao;

import models.Treatment;
import models.Patient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.*;

public class Sql2oTreatmentDaoTest {
    private Sql2oTreatmentDao treatmentDao;
    private Connection conn;
    private Sql2oPatientDao patientDao;

    @Before
    public void setUp() throws Exception {
        String connectionString = "jdbc:h2:mem:testing;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        Sql2o sql2o = new Sql2o(connectionString, "", "");
        treatmentDao = new Sql2oTreatmentDao(sql2o);
        patientDao = new Sql2oPatientDao(sql2o);
        conn = sql2o.open();

    }

    @After
    public void tearDown() throws Exception {
        conn.close();
    }

    public Treatment setupNewTreatment(){return new Treatment("chemotherapy", "6 months");
    }
    public Treatment setupNewTreatment2(){
        return new Treatment("radiation", "3 months");
    }
    public Patient setupNewPatient() { return new Patient("female", "stage 2", "Ann", 45, 1);
    }
    public Patient setupNewPatient2(){return new Patient("male","stage 4", "Bob", 55, 1);
    }
    @Test
    public void addingCourseSetsId() throws Exception {
        Treatment treatment = setupNewTreatment();
        int origTreatmentId = treatment.getId();
        treatmentDao.add(treatment);
        assertNotEquals(origTreatmentId, treatment.getId());
    }
    @Test
    public void existingTreatmentCanBeFoundById() throws Exception {
        Treatment treatment = setupNewTreatment();
        treatmentDao.add(treatment);
        Treatment foundTreatment = treatmentDao.findById(treatment.getId());
        assertEquals(treatment, foundTreatment);
    }
    @Test
    public void addedTreatmentsAreReturnedFromGetAll() throws Exception {
        Treatment treatment = setupNewTreatment();
        treatmentDao.add(treatment);
        assertEquals(1, treatmentDao.getAll().size());
    }
    @Test
    public void updateTreatmentsInfo() throws Exception {
        String initialTreatmentDescription = "chemotherapy";
        Treatment treatment = setupNewTreatment();
        treatmentDao.add(treatment);
        treatmentDao.update(treatment.getId(), "radiation", "6 months");
        Treatment updatedTreatment = treatmentDao.findById(treatment.getId());
        assertNotEquals(initialTreatmentDescription, updatedTreatment.getDescription());
    }

    @Test
    public void deleteTreatmentById(){
        Treatment treatment = setupNewTreatment();
        treatmentDao.add(treatment);
        treatmentDao.deleteTreatmentById(treatment.getId());
        assertEquals(0, treatmentDao.getAll().size());
    }

    @Test
    public void clearAllTreatments(){
        Treatment treatment = setupNewTreatment();
        Treatment treatment2 = setupNewTreatment2();
        treatmentDao.add(treatment);
        treatmentDao.add(treatment2);
        int daoSize = treatmentDao.getAll().size();
        treatmentDao.clearAllTreatments();
        assertTrue(daoSize > 0 && daoSize >treatmentDao.getAll().size());
    }
    @Test
    public void addTreatment_addsTreatmentToPatient() throws Exception {
        Patient testPatient = setupNewPatient();
        Patient otherPatient = setupNewPatient2();
        patientDao.add(testPatient);
        patientDao.add(otherPatient);

        Treatment testTreatment = setupNewTreatment();
        treatmentDao.add(testTreatment);

        treatmentDao.addTreatmentToPatient(testTreatment, testPatient);
        treatmentDao.addTreatmentToPatient(testTreatment, otherPatient);

        assertEquals(2, treatmentDao.getAllPatientsForATreatment(testTreatment.getId()).size());
    }
    @Test
    public void deletingTreatmentAlsoUpdatesJoinTable() throws Exception {
        Patient testPatient = setupNewPatient();
        patientDao.add(testPatient);

        Treatment testTreatment  = setupNewTreatment();
        treatmentDao.add(testTreatment);

        Treatment otherTreatment = setupNewTreatment2();
        treatmentDao.add(otherTreatment);

        treatmentDao.addTreatmentToPatient(testTreatment, testPatient);
        treatmentDao.addTreatmentToPatient(otherTreatment, testPatient);

        treatmentDao.deleteTreatmentById(testTreatment.getId());
        assertEquals(0, treatmentDao.getAllPatientsForATreatment(testTreatment.getId()).size());
    }
}
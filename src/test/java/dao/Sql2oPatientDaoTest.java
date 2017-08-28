package dao;

import models.Cancer;
import models.Treatment;
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
    private Sql2oTreatmentDao treatmentDao;
    private Sql2oCancerDao cancerDao;
    private Connection conn;

    @Before
    public void setUp() throws Exception {

        String connectionString = "jdbc:h2:mem:testing;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        Sql2o sql2o = new Sql2o(connectionString, "", "");
        patientDao = new Sql2oPatientDao(sql2o);
        treatmentDao = new Sql2oTreatmentDao(sql2o);
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
    public Patient setupNewPatient() { return new Patient("female", "stage 2", "Ann", 45, 1);
    }
    public Patient setupNewPatient2(){return new Patient("male","stage 4", "Bob", 55, 1);
    }
    public Treatment setupNewTreatment(){return new Treatment("chemotherapy", "6 months");
    }
    public Treatment setupNewTreatment2() {
        return new Treatment("radiation", "3 months");
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
        String initialGender = "female";
        Patient patient = setupNewPatient();
        patientDao.add(patient);
        patientDao.update(patient.getId(), "No Gender", "stage 2", "Ann", 45);
        Patient updatedPatient = patientDao.findById(patient.getId());
        assertNotEquals(initialGender, updatedPatient.getGender());

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
    public void addTreatment_addsPatientToTreatment() throws Exception {
    Treatment testTreatment = setupNewTreatment();
    Treatment otherTreatment = setupNewTreatment2();
    treatmentDao.add(testTreatment);
    treatmentDao.add(otherTreatment);

    Patient testPatient = setupNewPatient();
    patientDao.add(testPatient);

    patientDao.addPatientToTreatment(testPatient, testTreatment);
    patientDao.addPatientToTreatment(testPatient, otherTreatment);

    assertEquals(2, patientDao.getAllTreatmentsForAPatient(testPatient.getId()).size());
}


}
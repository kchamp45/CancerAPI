package models;

public class Patient {
    private String type;
    private String diagnosis;
    private int id;


    public Patient(String type, String diagnosis) {
        this.type = type;
        this.diagnosis = diagnosis;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Patient patient = (Patient) o;

        if (id != patient.id) return false;
        if (!type.equals(patient.type)) return false;
        return diagnosis.equals(patient.diagnosis);
    }

    @Override
    public int hashCode() {
        int result = type.hashCode();
        result = 31 * result + diagnosis.hashCode();
        result = 31 * result + id;
        return result;
    }
}

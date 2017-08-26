package models;

public class PatientA extends Patient {
        private String geneMutation;
        private int patientId;

    public PatientA (String gender,  String diagnosis, String name, int age, int cancerId, String geneMutation) {
        super(gender, diagnosis, name, age, cancerId);
        this.geneMutation = geneMutation;

    }

    public String getGeneMutation() {
        return geneMutation;
    }

    public void setGeneMutation(String geneMutation) {
        this.geneMutation = geneMutation;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        PatientA patientA = (PatientA) o;

        if (patientId != patientA.patientId) return false;
        return geneMutation.equals(patientA.geneMutation);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + geneMutation.hashCode();
        result = 31 * result + patientId;
        return result;
    }
}

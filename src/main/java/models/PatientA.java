package models;

public class PatientA extends Patient {
        private String name;
        private int age;
        private String geneMutation;
        private boolean famHistory;
        private int patientId;

    public PatientA (String type,  String diagnosis, String name, int age, String geneMutation, boolean famHistory) {
        super(type, diagnosis);
        this.name = name;
        this.age = age;
        this.geneMutation = geneMutation;
        this.famHistory = famHistory;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGeneMutation() {
        return geneMutation;
    }

    public void setGeneMutation(String geneMutation) {
        this.geneMutation = geneMutation;
    }

    public boolean isFamHistory() {
        return famHistory;
    }

    public void setFamHistory(boolean famHistory) {
        this.famHistory = famHistory;
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

        if (age != patientA.age) return false;
        if (famHistory != patientA.famHistory) return false;
        if (!name.equals(patientA.name)) return false;
        return geneMutation.equals(patientA.geneMutation);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + age;
        result = 31 * result + geneMutation.hashCode();
        result = 31 * result + (famHistory ? 1 : 0);
        return result;
    }
}

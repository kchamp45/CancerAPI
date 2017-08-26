package models;

public class Patient {
    private String gender;
    private String diagnosis;
    private String name;
    private int age;
    private int cancerId;
    private int id;


    public Patient(String gender, String diagnosis, String name, int age, int cancerId) {
        this.gender = gender;
        this.diagnosis = diagnosis;
        this.name = name;
        this.age = age;
        this.cancerId = cancerId;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCancerId() {
        return cancerId;
    }

    public void setCancerId(int cancerId) {
        this.cancerId = cancerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Patient patient = (Patient) o;

        if (age != patient.age) return false;
        if (cancerId != patient.cancerId) return false;
        if (id != patient.id) return false;
        if (!gender.equals(patient.gender)) return false;
        if (!diagnosis.equals(patient.diagnosis)) return false;
        return name.equals(patient.name);
    }

    @Override
    public int hashCode() {
        int result = gender.hashCode();
        result = 31 * result + diagnosis.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + age;
        result = 31 * result + cancerId;
        result = 31 * result + id;
        return result;
    }
}

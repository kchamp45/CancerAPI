package models;

public class Patient {
    private String type;
    private String name;
    private int age;
    private String diagnosis;
    private int id;


    public Patient(String type, String name, int age, String diagnosis) {
        this.type = type;
        this.name = name;
        this.age = age;
        this.diagnosis = diagnosis;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

        if (age != patient.age) return false;
        if (!type.equals(patient.type)) return false;
        if (!name.equals(patient.name)) return false;
        return diagnosis.equals(patient.diagnosis);
    }

    @Override
    public int hashCode() {
        int result = type.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + age;
        result = 31 * result + diagnosis.hashCode();
        return result;
    }
}

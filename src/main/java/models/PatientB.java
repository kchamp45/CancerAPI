package models;

public class PatientB extends Patient {
    private boolean famHistory;
    private int id;

    public PatientB  (String gender,  String diagnosis, String name, int age, int cancerId, boolean famHistory) {
        super(gender, diagnosis, name, age, cancerId);
        this.famHistory = famHistory;
    }

    public boolean isFamHistory() {
        return famHistory;
    }

    public void setFamHistory(boolean famHistory) {
        this.famHistory = famHistory;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        PatientB patientB = (PatientB) o;

        if (famHistory != patientB.famHistory) return false;
        return id == patientB.id;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (famHistory ? 1 : 0);
        result = 31 * result + id;
        return result;
    }
}

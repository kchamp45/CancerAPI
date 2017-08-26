package models;

public class Treatment {
    private String description;
    private String duration;
    private int id;

    public Treatment(String description, String duration) {
        this.description = description;
        this.duration = duration;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
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

        Treatment treatment = (Treatment) o;

        if (id != treatment.id) return false;
        if (!description.equals(treatment.description)) return false;
        return duration.equals(treatment.duration);
    }

    @Override
    public int hashCode() {
        int result = description.hashCode();
        result = 31 * result + duration.hashCode();
        result = 31 * result + id;
        return result;
    }
}
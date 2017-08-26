package models;

public class Cancer {
    private String type;
    private String description;
    private int id;

    public Cancer(String type, String description) {
        this.type = type;
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

        Cancer cancer = (Cancer) o;

        if (id != cancer.id) return false;
        if (!type.equals(cancer.type)) return false;
        return description.equals(cancer.description);
    }

    @Override
    public int hashCode() {
        int result = type.hashCode();
        result = 31 * result + description.hashCode();
        result = 31 * result + id;
        return result;
    }
}

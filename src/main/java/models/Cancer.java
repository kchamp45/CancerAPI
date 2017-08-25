package models;

public class Cancer {
        private String name;
        private String description;
        private int id;

        public Cancer(String name, String description) {
            this.name = name;
            this.description = description;
        }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        if (!name.equals(cancer.name)) return false;
        return description.equals(cancer.description);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + description.hashCode();
        result = 31 * result + id;
        return result;
    }
}

package by.Ahmed.jdbc.starter.entity;

public class Aircraft {
    private Long id;
    private String model;

    public Aircraft() {
    }

    public Aircraft(Long id, String model) {
        this.id = id;
        this.model = model;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    @Override
    public String toString() {
        return "Aircraft{" +
                "id=" + id +
                ", model='" + model + '\'' +
                '}';
    }
}

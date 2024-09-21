package model;


import java.util.Objects;

public class Task {

    private int id;
    private String name;
    protected Status status;
    private String description;
    private Epic epic;
    private Integer epicId;

    public Task(String name, String description) {
        this.name = name;
        this.status = Status.NEW;
        this.description = description;
    }

    public Task(int id, String name, Status status, String description, Integer epicId) {

        this.id = id;
        this.name = name;
        this.status = Status.NEW;
        this.description = description;
        this.epicId = getEpicId();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getEpicId() {

        return null;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id == task.id;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", status=" + status +
                ", description='" + description + '\'' +
                '}';
    }
}

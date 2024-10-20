package model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

public class Task {

    protected Status status;
    protected Duration duration;
    protected LocalDateTime startTime;
    private int id;
    private String name;
    private String description;


    public Task(int id, String name, Status status, String description, Duration duration, LocalDateTime startTime) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.description = description;
        this.duration = duration;
        this.startTime = startTime;
    }

    public Task(String name, String description, Duration duration, LocalDateTime startTime) {
        this.name = name;
        this.status = Status.NEW;
        this.description = description;
        this.duration = duration;
        this.startTime = startTime;

    }

    public Task(int id, String name, Status status, String description, int epicId, Duration duration, LocalDateTime startTime) {

        this.name = name;
        this.status = Status.NEW;
        this.description = description;
        this.duration = duration;
        this.startTime = startTime;

    }


    public Task(String name, String description) {
        this.name = name;
        this.status = Status.NEW;
        this.description = description;
    }

    public Task(int id, String name, Status status, String description, int epicId) {

        this.id = id;
        this.name = name;
        this.status = Status.NEW;
        this.description = description;

    }

    public Task(String name, Status status) {
        this.name = name;
        this.status = Status.NEW;

    }

    public Task(int id, String description, Status status) {
        this.id = id;
        this.description = description;
        this.status = Status.NEW;
    }

    public Task(int id, String name, String description, Status status) {

        this.id = id;
        this.name = name;
        this.description = description;
        this.status = Status.NEW;
    }

    public Task(int id, String name, String description) {

        this.id = id;
        this.name = name;
        this.description = description;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        if (startTime == null || duration == null) {
            return null;
        }
        return startTime.plus(duration);
    }

    public Duration getDuration() {

        return duration;
    }

    public TaskType getType() {
        return TaskType.TASK;
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


    @Override
    public int hashCode() {
        return Objects.hash(id, name, status, description);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Task)) return false;
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
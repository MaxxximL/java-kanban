//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package model;

import java.util.Objects;

public class Task {
    private int id;
    private String name;
    protected Status status;
    private String description;

    public Task(String name, String description) {
        this.name = name;
        this.status = Status.NEW;
        this.description = description;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Status getStatus() {
        return this.status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int hashCode() {
        return Objects.hash(new Object[]{this.id});
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o != null && this.getClass() == o.getClass()) {
            Task task = (Task)o;
            return this.id == task.id;
        } else {
            return false;
        }
    }

    public String toString() {
        int var10000 = this.id;
        return "Task{id=" + var10000 + ", name='" + this.name + "', status=" + String.valueOf(this.status) + ", description='" + this.description + "'}";
    }
}
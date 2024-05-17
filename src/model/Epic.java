
//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package model;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class Epic extends Task {
    private List<SubTask> subTasks = new ArrayList();

    public Epic(String name, String description) {
        super(name, description);
    }

    public List<SubTask> getSubTasks() {
        return this.subTasks;
    }

    public void addSubTask(SubTask subTask) {
        this.subTasks.add(subTask);
    }

    public void removeSubTask(SubTask subTask) {
        this.subTasks.remove(subTask);
    }

    public Status getStatus() {
        boolean allNew = this.subTasks.stream().allMatch((subTask) -> {
            return subTask.getStatus() == Status.NEW;
        });
        boolean allDone = this.subTasks.stream().allMatch((subTask) -> {
            return subTask.getStatus() == Status.DONE;
        });
        if (!this.subTasks.isEmpty() && !allNew) {
            return allDone ? Status.DONE : Status.IN_PROGRESS;
        } else {
            return Status.NEW;
        }
    }
}

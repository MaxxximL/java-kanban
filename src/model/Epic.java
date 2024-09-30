package model;

import java.util.ArrayList;
import java.util.List;

public class Epic extends Task {

    private int epicId;
    private List<SubTask> subTasks = new ArrayList<>();

    public Epic(int id, String name, Status status, String description, int epicId) {
        super(id, name, status, description, epicId);
        this.epicId = epicId;
    }

    public Epic(String name, String description) {
        super(name, description);
    }

    public Epic(int id, String name, String description) {
        super(id, name, description);
    }

    public List<SubTask> getSubTasks() {
        return subTasks;
    }

    public void addSubTask(SubTask subTask) {
        subTasks.add(subTask);
    }

    public void removeSubTask(SubTask subTask) {
        subTasks.remove(subTask);
    }

    public int getEpicId() {

        return epicId;
    }

    @Override
    public Status getStatus() {
        boolean allNew = subTasks.stream().allMatch(subTask -> subTask.getStatus() == Status.NEW);
        boolean allDone = subTasks.stream().allMatch(subTask -> subTask.getStatus() == Status.DONE);

        if (subTasks.isEmpty() || allNew) {
            return Status.NEW;
        } else if (allDone) {
            return Status.DONE;
        } else {
            return Status.IN_PROGRESS;
        }
    }
}

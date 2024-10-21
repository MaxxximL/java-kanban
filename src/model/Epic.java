package model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Epic extends Task {

    protected LocalDateTime endTime;
    private List<SubTask> subTasks = new ArrayList<>();

    public Epic(String name, String description, Duration duration, LocalDateTime startTime) {
        super(name, description, duration, startTime);

    }

    public Epic(String name, String description) {
        super(name, description, Duration.ZERO, LocalDateTime.now());
    }

    public Epic(int id, String name, Status status, String description, Duration duration, LocalDateTime startTime) {
        super(id, name, status, description, duration, startTime);
    }

    public List<SubTask> getSubTasks() {
        return subTasks;
    }

    public void addSubTask(SubTask subTask) {
        subTasks.add(subTask);
        updateEpicDetails();
    }

    public void removeSubTask(SubTask subTask) {
        subTasks.remove(subTask);
        updateEpicDetails();
    }

    private void updateEpicDetails() {
        if (subTasks.isEmpty()) {
            this.duration = Duration.ZERO;
            this.startTime = null;
            this.endTime = null;
        } else {
            this.duration = subTasks.stream()
                    .map(SubTask::getDuration)
                    .reduce(Duration.ZERO, Duration::plus);

            this.startTime = subTasks.stream()
                    .map(SubTask::getStartTime)
                    .filter(Objects::nonNull)
                    .min(LocalDateTime::compareTo)
                    .orElse(null);

            this.endTime = subTasks.stream()
                    .map(SubTask::getEndTime)
                    .filter(Objects::nonNull)
                    .max(LocalDateTime::compareTo)
                    .orElse(null);
        }
    }


    @Override
    public LocalDateTime getEndTime() {
        if (subTasks.isEmpty()) {
            return startTime;
        }
        return subTasks.stream()
                .map(SubTask::getEndTime)
                .max(LocalDateTime::compareTo)
                .orElse(startTime);
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
package service;

import model.Epic;
import model.Status;
import model.SubTask;

public class Managers {
    public static TaskManager getDefault() {
        return new InMemoryTaskManager();
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }

    public static void updatedEpicStatus(Epic epic) {
        Status newStatus = epic.getSubTasks().stream()
                .map(SubTask::getStatus)
                .reduce(Status.NEW, (current, status) -> {
                    if (status == Status.DONE) return Status.DONE;
                    if (current == Status.IN_PROGRESS || status == Status.IN_PROGRESS) return Status.IN_PROGRESS;
                    return current;
                });

        epic.setStatus(newStatus);

    }
}
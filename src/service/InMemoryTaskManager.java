package service;

import model.Epic;
import model.SubTask;
import model.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryTaskManager implements TaskManager {

    protected Map<String, Task> tasks = new HashMap<>();
    private Map<String, Epic> epics = new HashMap<>();
    private Map<String, SubTask> subTasks = new HashMap<>();
    private String idCounter = null;
    private HistoryManager historyManager = Managers.getDefaultHistory();

    private String generateId() {
        return idCounter;
    }

    @Override
    public Task createTask(Task task) {
        task.setId(generateId());
        tasks.put(task.getId(), task);

        return task;
    }

    @Override
    public Epic createEpic(Epic epic) {
        epic.setId(generateId());
        epics.put(epic.getId(), epic);
        return epic;
    }

    @Override
    public SubTask createSubTask(SubTask subTask) {
        subTask.setId(generateId());
        subTasks.put(subTask.getId(), subTask);

        return subTask;
    }

    @Override
    public Task getTaskById(String id) {
        Task task = tasks.get(id);
        if (task != null) {
            historyManager.add(task);

        }
        return task;
    }

    @Override
    public Epic getEpicById(String id) {
        Epic epic = epics.get(id);
        if (epic != null) {
            historyManager.add(epic);
        }
        return epic;
    }

    @Override
    public SubTask getSubTaskById(String id) {
        SubTask subTask = subTasks.get(id);
        if (subTask != null) {
            historyManager.add(subTask);
        }
        return subTask;
    }


    @Override
    public List<Task> getAllTasks() {
        return new ArrayList<>(tasks.values());
    }

    @Override
    public List<Epic> getAllEpics() {
        return new ArrayList<>(epics.values());
    }

    @Override
    public List<SubTask> getAllSubTasks() {
        return new ArrayList<>(subTasks.values());
    }

    @Override
    public void updateTask(Task task) {
        tasks.put(task.getId(), task);
    }

    @Override
    public void updateEpic(Epic epic) {
        epics.put(epic.getId(), epic);
        updateEpicStatus(epic);
    }

    @Override
    public void updateSubTask(SubTask subTask) {
        subTasks.put(subTask.getId(), subTask);

    }

    @Override
    public void deleteTask(String id) {
        tasks.remove(id);
        historyManager.remove(id);

    }

    @Override
    public void deleteEpic(String id) {
        Epic epic = epics.remove(id);
        if (epic != null) {
            for (SubTask subTask : epic.getSubTasks()) {
                subTasks.remove(subTask.getId());
            }
        }
    }

    @Override
    public void deleteSubTask(String id) {
        subTasks.remove(id);

    }

    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
    }

    private void updateEpicStatus(Epic epic) {
        epic.setStatus(epic.getStatus());
        epics.put(epic.getId(), epic);
    }
}

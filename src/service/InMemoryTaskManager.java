package service;

import model.Epic;
import model.SubTask;
import model.Task;

import java.time.LocalDateTime;
import java.util.*;

public class InMemoryTaskManager implements TaskManager {

    protected Map<Integer, Task> tasks = new HashMap<>();
    protected Map<Integer, Epic> epics = new HashMap<>();
    protected Map<Integer, SubTask> subTasks = new HashMap<>();
    protected int idCounter = 0;
    protected SortedSet<Task> prioritizedTasks = new TreeSet<>(Comparator.comparing(Task::getStartTime));
    private HistoryManager historyManager = Managers.getDefaultHistory();

    private int generateId() {
        return ++idCounter;
    }

    private boolean areOverlapping(Task t1, Task t2) {
        LocalDateTime start1 = t1.getStartTime();
        LocalDateTime end1 = t1.getEndTime();
        LocalDateTime start2 = t2.getStartTime();
        LocalDateTime end2 = t2.getEndTime();

        return (start1.isBefore(end2) && start2.isBefore(end1));
    }

    @Override
    public Task createTask(Task task) {
        if (task.getStartTime() != null) {
            for (Task existingTask : tasks.values()) {
                if (areOverlapping(existingTask, task)) {
                    throw new IllegalArgumentException("Задача пересекается с существующими задачами.");
                }
            }
            task.setId(generateId());
            tasks.put(task.getId(), task);
            prioritizedTasks.add(task);
        }
        return task;
    }

    public List<Task> getPrioritizedTasks() {

        return new ArrayList<>(prioritizedTasks);
    }

    @Override
    public Epic createEpic(Epic epic) {
        epic.setId(generateId());
        epics.put(epic.getId(), epic);
        return epic;
    }

    @Override
    public SubTask createSubTask(SubTask subTask) {
        Epic epic = epics.get(subTask.getEpicId());
        if (epic == null) {
            throw new IllegalArgumentException("Epic with ID " + subTask.getEpicId() + " not found.");
        }
        if (subTask.getStartTime() != null) {

            List<Task> allTasks = getAllTasks();
            allTasks.addAll(epic.getSubTasks());
            for (Task existingTask : allTasks) {
                if (areOverlapping(existingTask, subTask)) {
                    throw new IllegalArgumentException("Подзадача пересекается с существующими задачами или подзадачами.");
                }
            }

            subTask.setId(generateId());
            subTasks.put(subTask.getId(), subTask);
            epic.addSubTask(subTask);
            prioritizedTasks.add(subTask);
            updateEpicStatus(epic);
        }
        return subTask;
    }


    @Override
    public Task getTaskById(int id) {
        Task task = tasks.get(id);
        if (task != null) {
            historyManager.add(task);

        }
        return task;
    }

    @Override
    public Epic getEpicById(int id) {
        Epic epic = epics.get(id);
        if (epic != null) {
            historyManager.add(epic);
        }
        return epic;
    }

    @Override
    public SubTask getSubTaskById(int id) {
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
        if (task == null || !tasks.containsKey(task.getId())) {
            throw new IllegalArgumentException("Task with ID " + task.getId() + " does not exist.");
        }


        if (task.getStartTime() != null) {
            for (Task existingTask : tasks.values()) {
                if (existingTask.getId() != task.getId() && areOverlapping(existingTask, task)) {
                    throw new IllegalArgumentException("Task overlaps with existing tasks.");
                }
            }
        }

        tasks.put(task.getId(), task);
        prioritizedTasks.add(task);
    }

    @Override
    public void updateEpic(Epic epic) {
        if (epic == null || !epics.containsKey(epic.getId())) {
            throw new IllegalArgumentException("Epic with ID " + epic.getId() + " does not exist.");
        }

        epics.put(epic.getId(), epic);
        Managers.updatedEpicStatus(epic);
    }

    @Override
    public void updateSubTask(SubTask subTask) {
        if (subTask == null || !subTasks.containsKey(subTask.getId())) {
            throw new IllegalArgumentException("SubTask with ID " + subTask.getId() + " does not exist.");
        }

        Epic epic = epics.get(subTask.getEpicId());
        if (epic == null) {
            throw new IllegalArgumentException("Epic with ID " + subTask.getEpicId() + " not found.");
        }


        if (subTask.getStartTime() != null) {
            List<Task> allTasks = getAllTasks();
            allTasks.addAll(epic.getSubTasks());
            for (Task existingTask : allTasks) {
                if (existingTask.getId() != subTask.getId() && areOverlapping(existingTask, subTask)) {
                    throw new IllegalArgumentException("SubTask overlaps with existing tasks or subTasks.");
                }
            }
        }

        subTasks.put(subTask.getId(), subTask);
        epic.removeSubTask(subTask);
        epic.addSubTask(subTask);
        Managers.updatedEpicStatus(epic);
    }

    @Override
    public void deleteTask(int id) {
        Task removedTask = tasks.remove(id);
        if (removedTask != null) {
            prioritizedTasks.remove(removedTask);
        }
        historyManager.remove(id);
    }

    @Override
    public void deleteEpic(int id) {
        Epic epic = epics.remove(id);
        if (epic != null) {
            for (SubTask subTask : epic.getSubTasks()) {
                subTasks.remove(subTask.getId());
            }
        }
    }

    @Override
    public void deleteSubTask(int id) {
        SubTask subTask = subTasks.remove(id);
        if (subTask != null) {
            Epic epic = epics.get(subTask.getEpicId());
            if (epic != null) {
                epic.removeSubTask(subTask);
            }
            prioritizedTasks.remove(subTask);
            updateEpicStatus(epic);
        }
    }


    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
    }

    private void updateEpicStatus(Epic epic) {

        Managers.updatedEpicStatus(epic);
    }
}
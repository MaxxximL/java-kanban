package service;

import model.Epic;
import model.Status;
import model.SubTask;
import model.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class TaskManager {

    private HashMap<Integer, Task> tasks;

    private HashMap<Integer, Epic> epics;

    private HashMap<Integer, SubTask> subTasks;

    private int seq = 0;

    public TaskManager() {
        this.tasks = new HashMap<>();
        this.epics = new HashMap<>();
        this.subTasks = new HashMap<>();

    }

    public  int generateId() {

        return ++seq;
    }

    public Task get(int id) {
        return tasks.get(id);

    }

    public Task create(Task task) {

        task.setId(generateId());
        tasks.put(task.getId(), task);
        return task;
    }

    public void update(Task task) {

        tasks.put(task.getId(), task);

    }

    public ArrayList<Task> getAll() {

        return new ArrayList<>(tasks.values());


    }

    public ArrayList<Epic> getAllEpics() {

        return new ArrayList<>(epics.values());
    }






    public Epic getEpicId(int id) {
        return epics.get(id);

    }

public Epic createEpic(Epic epic) {
        epics.put(epic.getId(), epic);
        return epic;
}


    public void updateEpic(Epic epic) {

        Epic saved = epics.get(epic.getId());
        if (saved == null) {
            return;

        }

        saved.setName(epic.getName());
        saved.setDescription(epic.getDescription());
    }



    public SubTask createSubTask(SubTask subTask) {
        Epic epic = epics.get(subTask.getEpic().getId());
        epic.addTask(subTask);
        epic.updateStatus();
        return subTask;
    }

    public void updateSubTask(SubTask subTask) {

        Epic epic = subTask.getEpic();
        Epic savedEpic = epics.get(epic.getId());
        if (savedEpic == null) {
            return;
        }

        calculateStatus(savedEpic);
        savedEpic.updateStatus();
    }



    public void delete(int id) {
        tasks.remove(id);

    }

    public void deleteEpic(int id) {
        epics.remove(id);

    }



    public void deleteSubTask(int id) {

        SubTask removeSubTask = subTasks.remove(id);


        Epic epic = removeSubTask.getEpic();
        Epic epicSaved = epics.get(epic.getId());

        epicSaved.getSubTasks().remove(removeSubTask);
        calculateStatus(epicSaved);

    }

  private void calculateStatus(Epic epic) {
        Status status = Status.NEW;
        epic.setStatus(status);
    }



}

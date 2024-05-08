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

    public int generateId() {

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
        subTask.setId(generateId());
        Epic epic = epics.get(subTask.getEpic().getId());
        epic.setSubTasks(subTask);

        return subTask;
    }

    public void updateSubTask(SubTask subTask1, Status status, SubTask subTask2) {

        Epic epic = subTask1.getEpic();
        Epic epic1 = subTask2.getEpic();

        Epic savedEpic = epics.get(epic.getId());
        Epic savedEpic1 = epics.get(epic1.getId());


        if (savedEpic == null) {
            return;
        }

        calculateStatus(savedEpic, savedEpic1, status, subTask1, subTask2);

    }


    public void delete(int id) {
        tasks.remove(id);

    }

    public void deleteEpic(int id) {
        epics.remove(id);

    }


    public void deleteSubTask(int id) {

        SubTask removeSubTask1 = subTasks.remove(id);
        SubTask removeSubTask2 = subTasks.remove(id);

        Epic epic = removeSubTask1.getEpic();
        epic = removeSubTask2.getEpic();
        Epic epicSaved = epics.get(epic.getId());

        epicSaved.getSubTasks().remove(removeSubTask1);
        epicSaved.getSubTasks().remove(removeSubTask2);


    }


    public void calculateStatus(Epic savedEpic, Epic savedEpic1, Status status, SubTask subTask1, SubTask subTask2) {


        if (subTask1.getStatus(status).equals(Status.NEW) || (subTask2.getStatus(status).equals(Status.NEW))) {

            savedEpic.setStatus(Status.NEW);


        } else if (subTask1.getStatus(status).equals(Status.NEW) || (subTask2.getStatus(status).equals(Status.IN_PROGRESS))) {


            savedEpic1.setStatus(Status.IN_PROGRESS);



        } else if (subTask1.getStatus(status).equals(Status.DONE) || (subTask2.getStatus(status).equals(Status.DONE))) {


            savedEpic.setStatus(Status.DONE);

        }
    }
}








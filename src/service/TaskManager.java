
//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import model.Epic;
import model.SubTask;
import model.Task;

public class TaskManager {
    private Map<Integer, Task> tasks = new HashMap();
    private Map<Integer, Epic> epics = new HashMap();
    private Map<Integer, SubTask> subTasks = new HashMap();
    private int idCounter = 0;

    public TaskManager() {
    }

    private int generateId() {
        return ++this.idCounter;
    }

    public Task createTask(Task task) {
        task.setId(this.generateId());
        this.tasks.put(task.getId(), task);
        return task;
    }

    public Epic createEpic(Epic epic) {
        epic.setId(this.generateId());
        this.epics.put(epic.getId(), epic);
        return epic;
    }

    public SubTask createSubTask(SubTask subTask) {
        subTask.setId(this.generateId());
        this.subTasks.put(subTask.getId(), subTask);
        Epic epic = subTask.getEpic();
        epic.addSubTask(subTask);
        this.updateEpicStatus(epic);
        return subTask;
    }

    public Task getTaskById(int id) {
        return (Task)this.tasks.get(id);
    }

    public Epic getEpicById(int id) {
        return (Epic)this.epics.get(id);
    }

    public SubTask getSubTaskById(int id) {
        return (SubTask)this.subTasks.get(id);
    }

    public List<Task> getAllTasks() {
        return new ArrayList(this.tasks.values());
    }

    public List<Epic> getAllEpics() {
        return new ArrayList(this.epics.values());
    }

    public List<SubTask> getAllSubTasks() {
        return new ArrayList(this.subTasks.values());
    }

    public void updateTask(Task task) {
        this.tasks.put(task.getId(), task);
    }

    public void updateEpic(Epic epic) {
        this.epics.put(epic.getId(), epic);
        this.updateEpicStatus(epic);
    }

    public void updateSubTask(SubTask subTask) {
        this.subTasks.put(subTask.getId(), subTask);
        this.updateEpicStatus(subTask.getEpic());
    }

    public void deleteTask(int id) {
        this.tasks.remove(id);
    }

    public void deleteEpic(int id) {
        Epic epic = (Epic)this.epics.remove(id);
        if (epic != null) {
            Iterator var4 = epic.getSubTasks().iterator();

            while(var4.hasNext()) {
                SubTask subTask = (SubTask)var4.next();
                this.subTasks.remove(subTask.getId());
            }
        }

    }

    public void deleteSubTask(int id) {
        SubTask subTask = (SubTask)this.subTasks.remove(id);
        if (subTask != null) {
            Epic epic = subTask.getEpic();
            epic.removeSubTask(subTask);
            this.updateEpicStatus(epic);
        }

    }

    private void updateEpicStatus(Epic epic) {
        epic.setStatus(epic.getStatus());
        this.epics.put(epic.getId(), epic);
    }
}
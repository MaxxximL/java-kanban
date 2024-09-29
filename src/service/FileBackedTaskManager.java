package service;

import model.Epic;
import model.SubTask;
import model.Task;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import java.nio.file.Paths;
import java.util.List;

public class FileBackedTaskManager extends InMemoryTaskManager {

    private Path file;

    public FileBackedTaskManager(Path file) {
        this.file = file;
        try {
            if (Files.exists(file)) {
                loadFromFile();
            } else {
                Files.createFile(file);
            }
        } catch (IOException e) {
            throw new ManagerSaveException("Error creating or loading file", e);
        }
    }

    public static FileBackedTaskManager loadFromFile(Path file) {
        FileBackedTaskManager taskManager = new FileBackedTaskManager(file);
        try (BufferedReader br = Files.newBufferedReader(file)) {
            String line;
            while ((line = br.readLine()) != null) {
                Task task = CSVFormatter.fromString(line);
                taskManager.createTask(task);
            }
            for (Epic epic : taskManager.getAllEpics()) {
                for (SubTask subTask : epic.getSubTasks()) {
                    taskManager.createSubTask(subTask);
                }
            }

        } catch (IOException e) {
            throw new ManagerSaveException("Error loading from file", e);
        }
        return taskManager;
    }

    @Override
    public Task createTask(Task task) {
        Task createdTask = super.createTask(task);
        save();

        return createdTask;
    }

    @Override
    public Epic createEpic(Epic epic) {
        Epic createdEpic = super.createEpic(epic);
        save();

        return createdEpic;
    }

    @Override
    public SubTask createSubTask(SubTask subTask) {
        SubTask createdSubTask = super.createSubTask(subTask);
        save();

        return createdSubTask;
    }

    @Override
    public void deleteTask(int id) {
        super.deleteTask(id);
        save();
    }

    @Override
    public void deleteEpic(int id) {
        super.deleteEpic(id);
        save();
    }

    @Override
    public void deleteSubTask(int id) {
        super.deleteSubTask(id);
        save();
    }

    @Override
    public void updateTask(Task task) {
        super.updateTask(task);
        save();
    }

    @Override
    public void updateEpic(Epic epic) {
        super.updateEpic(epic);
        save();
    }

    @Override
    public void updateSubTask(SubTask subTask) {
        super.updateSubTask(subTask);
        save();
    }


    public void save() {
        try (BufferedWriter bw = Files.newBufferedWriter(file)) {
            bw.write(CSVFormatter.getHeader());
            bw.newLine();
            for (Task task : tasks.values()) {
                bw.write(CSVFormatter.toString(task));
                bw.newLine();
            }
            for (Epic epic : epics.values()) {
                bw.write(CSVFormatter.toString(epic));
                bw.newLine();
                for (SubTask subTask : epic.getSubTasks()) {
                    bw.write(CSVFormatter.toString(subTask));
                    bw.newLine();
                }
            }
        } catch (IOException e) {
            throw new ManagerSaveException("Error saving to file", e);
        }
    }

    private void loadFromFile() {
        try (BufferedReader br = Files.newBufferedReader(file)) {
            String line;
            int maxId = 0;
            while ((line = br.readLine()) != null) {
                Task task = CSVFormatter.fromString(line);
                tasks.put(task.getId(), task);
                if (task instanceof Epic) {
                    Epic epic = (Epic) task;
                    for (SubTask subTask : epic.getSubTasks()) {
                        tasks.put(subTask.getId(), subTask);
                    }
                }
                maxId = Math.max(maxId, task.getId());
            }
            idCounter = maxId + 1;
        } catch (IOException e) {
            throw new ManagerSaveException("Error loading from file", e);
        }
    }

    public static void main(String[] args) {
        Path file = Paths.get("tasks.csv");
        FileBackedTaskManager taskManager = FileBackedTaskManager.loadFromFile(file);

        Task task1 = new Task("Task 1", "Description 1");
        Task task2 = new Task("Task 2", "Description 2");
        Epic epic1 = new Epic("Epic 1", "Description 1");
        SubTask subTask1 = new SubTask("SubTask 1", "Description 1", epic1.getId());
        SubTask subTask2 = new SubTask("SubTask 2", "Description 2", epic1.getId());

        taskManager.createTask(task1);
        taskManager.createTask(task2);
        taskManager.createEpic(epic1);
        taskManager.createSubTask(subTask1);
        taskManager.createSubTask(subTask2);

        System.out.println("Tasks: " + taskManager.getAllTasks());
        System.out.println("Epics: " + taskManager.getAllEpics());
        System.out.println("SubTasks: " + taskManager.getAllSubTasks());

        taskManager.updateTask(task1);
        taskManager.updateEpic(epic1);
        taskManager.updateSubTask(subTask1);
        taskManager.updateSubTask(subTask2);

        System.out.println("Updated Tasks: " + taskManager.getAllTasks());
        System.out.println("Updated Epics: " + taskManager.getAllEpics());
        System.out.println("Updated SubTasks: " + taskManager.getAllSubTasks());

        taskManager.deleteTask(task1.getId());
        taskManager.deleteEpic(epic1.getId());
        taskManager.deleteSubTask(subTask1.getId());
        taskManager.deleteSubTask(subTask2.getId());

        System.out.println("After deletion: ");
        System.out.println("Tasks: " + taskManager.getAllTasks());
        System.out.println("Epics: " + taskManager.getAllEpics());
        System.out.println("SubTasks: " + taskManager.getAllSubTasks());
    }
}
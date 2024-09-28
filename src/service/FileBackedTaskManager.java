package service;

import model.Epic;
import model.SubTask;
import model.Task;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileBackedTaskManager extends InMemoryTaskManager {

    private Path file;

    public FileBackedTaskManager(Path file) {
        this.file = file;
        try {
            Files.createFile(file);
        } catch (IOException e) {
            throw new ManagerSaveException("Error creating file", e);
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
            for (Task task : tasks.values()) {
                bw.write(CSVFormatter.toString(task));
                bw.newLine();
            }
        } catch (IOException e) {
            throw new ManagerSaveException("Error saving to file", e);
        }
    }
}

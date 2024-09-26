package service;

import model.Epic;
import model.SubTask;
import model.Task;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FileBackedTaskManager extends InMemoryTaskManager {

    private static final String PATH_TO_FILE = "/resources/path/to/file";
    private final Path file;

    public FileBackedTaskManager(Path file) {

        this.file = file;

    }

    public static FileBackedTaskManager loadFromFile() {
        FileBackedTaskManager fileBackedTaskManager = new FileBackedTaskManager(Path.of(PATH_TO_FILE));

        try (BufferedReader br = Files.newBufferedReader(Path.of(PATH_TO_FILE))) {
            String line;
            List<Task> tasks = new ArrayList<>();
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                int id = Integer.parseInt(parts[0]);
                Task task = CSVFormatter.fromString(line);
                tasks.add(task);
            }
        } catch (IOException e) {
            throw ManagerSaveException.loadException(e);
        }
        return fileBackedTaskManager;
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

    protected void save() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file.toFile()))) {
            bw.write(CSVFormatter.getHeader());
            bw.newLine();
            for (Map.Entry<Integer, Task> entry : tasks.entrySet()) {
                bw.write(entry.getValue().toString());

                bw.newLine();
            }
        } catch (IOException e) {
            throw ManagerSaveException.saveException(e);

        }

    }

}

package service;

import model.Epic;
import model.SubTask;
import model.Task;
import model.TaskType;

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
            int maxId = -1;
            while ((line = br.readLine()) != null) {
                Task task = CSVFormatter.fromString(line);
                TaskType type = task.getType();

                if (type == TaskType.TASK) {
                    tasks.put(task.getId(), task);

                } else if (type == TaskType.EPIC) {
                    Epic epic = (Epic) task;
                    epics.put(task.getId(), epic);

                } else if (type == TaskType.SUBTASK) {
                    SubTask subTask = (SubTask) task;
                    subTasks.put(task.getId(), subTask);
                    final int epicId = subTask.getEpicId();
                    epics.get(epicId).getSubTasks().add(subTask);

                }
                maxId = Math.max(maxId, task.getId());
            }
            idCounter = maxId + 1;
        } catch (IOException e) {
            throw new ManagerSaveException("Error loading from file", e);
        }
    }
}

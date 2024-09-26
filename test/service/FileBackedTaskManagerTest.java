package service;

import model.Epic;
import model.SubTask;
import model.Status;
import model.Task;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FileBackedTaskManagerTest {

    @Test
    public void testSaveAndLoadEmptyFile() throws IOException {
        Path file = Files.createTempFile("test", ".txt");
        FileBackedTaskManager taskManager = new FileBackedTaskManager(file);

        taskManager.save();

        List<Task> tasks = new ArrayList<>();
        try (BufferedReader br = Files.newBufferedReader(file)) {
            String line;
            while ((line = br.readLine()) != null) {
                tasks.add(CSVFormatter.fromString(line));
            }
        }

        assertEquals(0, tasks.size());
    }

    @Test
    public void testSaveAndLoadSingleTask() throws IOException {
        Path file = Files.createTempFile("test", ".txt");
        FileBackedTaskManager taskManager = new FileBackedTaskManager(file);

        Task task1 = new Task("Task 1", "Description 1");
        taskManager.createTask(task1);
        taskManager.save();

        List<Task> tasks = new ArrayList<>();
        try (BufferedReader br = Files.newBufferedReader(file)) {
            String line;
            while ((line = br.readLine()) != null) {
                tasks.add(CSVFormatter.fromString(line));
            }
        }

        assertEquals(1, tasks.size());
        assertEquals(task1.getName(), tasks.get(0).getName());
        assertEquals(task1.getDescription(), tasks.get(0).getDescription());
    }

    @Test
    public void testSaveAndLoadMultipleTasks() throws IOException {
        Path file = Files.createTempFile("test", ".txt");
        FileBackedTaskManager taskManager = new FileBackedTaskManager(file);

        Task task1 = new Task("Task 1", "Description 1");
        Task task2 = new Task("Task 2", "Description 2");
        taskManager.createTask(task1);
        taskManager.createTask(task2);
        taskManager.save();

        List<Task> tasks = new ArrayList<>();
        try (BufferedReader br = Files.newBufferedReader(file)) {
            String line;
            while ((line = br.readLine()) != null) {
                tasks.add(CSVFormatter.fromString(line));
            }
        }

        assertEquals(2, tasks.size());
        assertEquals(task1.getName(), tasks.get(0).getName());
        assertEquals(task1.getDescription(), tasks.get(0).getDescription());
        assertEquals(task2.getName(), tasks.get(1).getName());
        assertEquals(task2.getDescription(), tasks.get(1).getDescription());
    }

    @Test
    public void testSaveAndLoadEpicWithSubTasks() throws IOException {
        Path file = Files.createTempFile("test", ".txt");
        FileBackedTaskManager taskManager = new FileBackedTaskManager(file);

        Epic epic1 = new Epic("Epic 1", "Description 1");
        SubTask subTask1 = new SubTask("SubTask 1", "Description 1", epic1.getId());
        SubTask subTask2 = new SubTask("SubTask 2", "Description 2", epic1.getId());
        taskManager.createEpic(epic1);
        taskManager.createSubTask(subTask1);
        taskManager.createSubTask(subTask2);
        taskManager.save();

        List<Task> tasks = new ArrayList<>();
        try (BufferedReader br = Files.newBufferedReader(file)) {
            String line;
            while ((line = br.readLine()) != null) {
                tasks.add(CSVFormatter.fromString(line));
            }
        }

        assertEquals(3, tasks.size());
        assertEquals(epic1.getName(), tasks.get(0).getName());
        assertEquals(epic1.getDescription(), tasks.get(0).getDescription());
        assertEquals(subTask1.getName(), tasks.get(1).getName());
        assertEquals(subTask1.getDescription(), tasks.get(1).getDescription());
        assertEquals(subTask2.getName(), tasks.get(2).getName());
        assertEquals(subTask2.getDescription(), tasks.get(2).getDescription());
    }
}
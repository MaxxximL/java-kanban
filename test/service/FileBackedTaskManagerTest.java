package service;

import model.Epic;
import model.SubTask;
import model.Task;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;


import java.util.List;

public class FileBackedTaskManagerTest {

    private FileBackedTaskManager taskManager;

    @BeforeEach
    public void setUp() throws Exception {
        Path file = Paths.get("path/to/file");
        taskManager = new FileBackedTaskManager(file);
    }

    @Test
    public void testSaveAndLoadEmptyFile() throws IOException {
        taskManager.save();
        File file = file.toFile();
        boolean exists = file.exists();
        Assertions.assertTrue(exists);
        taskManager = FileBackedTaskManager.loadFromFile(file);
        List<Task> tasks = taskManager.getAllTasks();
        Assertions.assertTrue(tasks.isEmpty());
    }

    @Test
    public void testSaveAndLoadMultipleTasks() throws IOException {
        Task task1 = new Task("Task 1", "Description 1");
        Task task2 = new Task("Task 2", "Description 2");
        taskManager.createTask(task1);
        taskManager.createTask(task2);
        taskManager.save();
        File file = file.toFile();
        boolean exists = file.exists();
        Assertions.assertTrue(exists);
        taskManager = FileBackedTaskManager.loadFromFile(file);
        List<Task> tasks = taskManager.getAllTasks();
        Assertions.assertEquals(2, tasks.size());
        Assertions.assertTrue(tasks.contains(task1));
        Assertions.assertTrue(tasks.contains(task2));
    }

    @Test
    public void testSaveAndLoadMultipleEpics() throws IOException {
        Epic epic1 = new Epic("Epic 1", "Description 1");
        Epic epic2 = new Epic("Epic 2", "Description 2");
        taskManager.createEpic(epic1);
        taskManager.createEpic(epic2);
        taskManager.save();
        File file = file.toFile();
        boolean exists = file.exists();
        Assertions.assertTrue(exists);
        taskManager = FileBackedTaskManager.loadFromFile(file);
        List<Epic> epics = taskManager.getAllEpics();
        Assertions.assertEquals(2, epics.size());
        Assertions.assertTrue(epics.contains(epic1));
        Assertions.assertTrue(epics.contains(epic2));
    }

    @Test
    public void testSaveAndLoadMultipleSubTasks() throws IOException {
        SubTask subTask1 = new SubTask("SubTask 1", "Description 1", null);
        SubTask subTask2 = new SubTask("SubTask 2", "Description 2", null);
        taskManager.createSubTask(subTask1);
        taskManager.createSubTask(subTask2);
        taskManager.save();
        File file = file.toFile();
        boolean exists = file.exists();
        Assertions.assertTrue(exists);
        taskManager = FileBackedTaskManager.loadFromFile(file);
        List<SubTask> subTasks = taskManager.getAllSubTasks();
        Assertions.assertEquals(2, subTasks.size());
        Assertions.assertTrue(subTasks.contains(subTask1));
        Assertions.assertTrue(subTasks.contains(subTask2));
    }
}
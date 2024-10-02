package service;

import model.Task;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class FileBackedTaskManagerTest {

    @Test
    public void testSaveAndLoadEmptyFile() throws IOException {
        File tempFile = File.createTempFile("temp", ".txt");
        FileBackedTaskManager taskManager = new FileBackedTaskManager(tempFile.toPath());
        taskManager.save();

        List<Task> tasks = taskManager.getAllTasks();
        assertEquals(0, tasks.size());
    }

    @Test
    public void testSaveAndLoadSingleTask() throws IOException {
        File tempFile = File.createTempFile("temp", ".txt");
        FileBackedTaskManager taskManager = new FileBackedTaskManager(tempFile.toPath());
        Task task = new Task("Task 1", "Description 1");
        taskManager.createTask(task);
        taskManager.save();

        List<Task> tasks = taskManager.getAllTasks();
        assertEquals(1, tasks.size());
        assertEquals(task, tasks.get(0));
    }

    @Test
    public void testSaveAndLoadMultipleTasks() throws IOException {
        File tempFile = File.createTempFile("temp", ".txt");
        FileBackedTaskManager taskManager = new FileBackedTaskManager(tempFile.toPath());
        Task task1 = new Task("Task 1", "Description 1");
        Task task2 = new Task("Task 2", "Description 2");
        Task task3 = new Task("Task 3", "Description 3");
        taskManager.createTask(task1);
        taskManager.createTask(task2);
        taskManager.createTask(task3);
        taskManager.save();

        List<Task> tasks = taskManager.getAllTasks();
        assertEquals(3, tasks.size());
        assertEquals(task1, tasks.get(0));
        assertEquals(task2, tasks.get(1));
        assertEquals(task3, tasks.get(2));
    }


}
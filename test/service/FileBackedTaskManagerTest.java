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

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class FileBackedTaskManagerTest {

    @Test
    public void testSaveAndLoadEmptyFile() throws IOException {
        Path file = Paths.get("temp.txt");
        FileBackedTaskManager taskManager = new FileBackedTaskManager(file);
        taskManager.save();

        List<Task> tasks = taskManager.getAllTasks();
        assertEquals(0, tasks.size());
    }

    @Test
    public void testSaveAndLoadSingleTask() throws IOException {
        Path file = Paths.get("temp.txt");
        FileBackedTaskManager taskManager = new FileBackedTaskManager(file);
        Task task = new Task("Task 1", "Description 1");
        taskManager.createTask(task);
        taskManager.save();

        List<Task> tasks = taskManager.getAllTasks();
        assertEquals(1, tasks.size());
        assertEquals(task, tasks.get(0));
    }

    @Test
    public void testSaveAndLoadMultipleTasks() throws IOException {
        Path file = Paths.get("temp.txt");
        FileBackedTaskManager taskManager = new FileBackedTaskManager(file);
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

    @Test
    public void testLoadInvalidFile() {
        Path file = Paths.get("invalid_file.txt");
        assertThrows(IOException.class, () -> new FileBackedTaskManager(file));
    }
}
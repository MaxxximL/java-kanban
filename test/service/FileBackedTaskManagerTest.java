package service;

import model.Epic;
import model.SubTask;
import model.Task;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class FileBackedTaskManagerTest {


    @Test
    public void testSaveAndLoadEmptyFile() throws IOException {
        Path file = Paths.get("path/to/file");
        FileBackedTaskManager taskManager = new FileBackedTaskManager(file);

        taskManager.save();

        FileBackedTaskManager loadedTaskManager = FileBackedTaskManager.loadFromFile();

        assertEquals(0, loadedTaskManager.getAllTasks().size());
        assertEquals(0, loadedTaskManager.getAllEpics().size());
        assertEquals(0, loadedTaskManager.getAllSubTasks().size());
    }

    @Test
    public void testSaveAndLoadSingleTask() throws IOException {
        Path file = Paths.get("path/to/file");
        FileBackedTaskManager taskManager = new FileBackedTaskManager(file);

        Task task = new Task("Task 1", "Description 1");
        taskManager.createTask(task);

        taskManager.save();

        FileBackedTaskManager loadedTaskManager = FileBackedTaskManager.loadFromFile();

        List<Task> tasks = loadedTaskManager.getAllTasks();
        assertEquals(1, tasks.size());
        assertEquals(task, tasks.get(0));
    }

    @Test
    public void testSaveAndLoadMultipleTasks() throws IOException {
        Path file = Paths.get("path/to/file");
        FileBackedTaskManager taskManager = new FileBackedTaskManager(file);

        Task task1 = new Task("Task 1", "Description 1");
        Task task2 = new Task("Task 2", "Description 2");

        taskManager.createTask(task1);
        taskManager.createTask(task2);

        taskManager.save();

        FileBackedTaskManager loadedTaskManager = FileBackedTaskManager.loadFromFile();

        List<Task> tasks = loadedTaskManager.getAllTasks();
        assertEquals(2, tasks.size());
        assertEquals(task1, tasks.get(0));
        assertEquals(task2, tasks.get(1));
    }

    @Test
    public void testSaveAndLoadSingleEpic() throws IOException {
        Path file = Paths.get("path/to/file");
        FileBackedTaskManager taskManager = new FileBackedTaskManager(file);

        Epic epic = new Epic("Epic 1", "Description 1");
        taskManager.createEpic(epic);

        taskManager.save();

        FileBackedTaskManager loadedTaskManager = FileBackedTaskManager.loadFromFile();

        List<Epic> epics = loadedTaskManager.getAllEpics();
        assertEquals(1, epics.size());
        assertEquals(epic, epics.get(0));
    }

    @Test
    public void testSaveAndLoadMultipleEpics() throws IOException {
        Path file = Paths.get("path/to/file");
        FileBackedTaskManager taskManager = new FileBackedTaskManager(file);

        Epic epic1 = new Epic("Epic 1", "Description 1");
        Epic epic2 = new Epic("Epic 2", "Description 2");

        taskManager.createEpic(epic1);
        taskManager.createEpic(epic2);

        taskManager.save();

        FileBackedTaskManager loadedTaskManager = FileBackedTaskManager.loadFromFile();

        List<Epic> epics = loadedTaskManager.getAllEpics();
        assertEquals(2, epics.size());
        assertEquals(epic1, epics.get(0));
        assertEquals(epic2, epics.get(1));
    }

    @Test
    public void testSaveAndLoadSingleSubtask() throws IOException {
        Path file = Paths.get("path/to/file");
        FileBackedTaskManager taskManager = new FileBackedTaskManager(file);

        SubTask subtask = new SubTask("Subtask 1", "Description 1", 1);
        taskManager.createSubTask(subtask);

        taskManager.save();

        FileBackedTaskManager loadedTaskManager = FileBackedTaskManager.loadFromFile();

        List<SubTask> subtasks = loadedTaskManager.getAllSubTasks();
        assertEquals(1, subtasks.size());
        assertEquals(subtask, subtasks.get(0));
    }

    @Test
    public void testSaveAndLoadMultipleSubtasks() throws IOException {
        Path file = Paths.get("path/to/file");
        FileBackedTaskManager taskManager = new FileBackedTaskManager(file);

        SubTask subtask1 = new SubTask("Subtask 1", "Description 1", 1);
        SubTask subtask2 = new SubTask("Subtask 2", "Description 2", 1);

        taskManager.createSubTask(subtask1);
        taskManager.createSubTask(subtask2);

        taskManager.save();

        FileBackedTaskManager loadedTaskManager = FileBackedTaskManager.loadFromFile();

        List<SubTask> subtasks = loadedTaskManager.getAllSubTasks();
        assertEquals(2, subtasks.size());
        assertEquals(subtask1, subtasks.get(0));
        assertEquals(subtask2, subtasks.get(1));
    }
}


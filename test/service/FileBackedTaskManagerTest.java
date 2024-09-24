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
    public void testSaveAndLoadTask() throws IOException {
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
    public void testSaveAndLoadEpic() throws IOException {
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
    public void testSaveAndLoadSubtask() throws IOException {
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


package service;

import model.Task;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;


public class FileBackedTaskManagerTest {

    private FileBackedTaskManager taskManager;

    @BeforeEach
    public void setUp() throws Exception {
        Path file = Paths.get("path/to/file");
        Files.createFile(file);
        taskManager = new FileBackedTaskManager(file);
    }

    @Test
    public void testSaveAndLoadEmptyFile() throws IOException {
        taskManager.save();
        File file = new File("path/to/file");
        boolean exists = file.exists();
        Assertions.assertTrue(exists);
        taskManager = FileBackedTaskManager.loadFromFile();
        List<Task> tasks = taskManager.getAllTasks();
        Assertions.assertTrue(tasks.isEmpty());
    }
}
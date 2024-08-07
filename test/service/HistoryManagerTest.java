package service;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import model.Task;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HistoryManagerTest {

    private HistoryManager historyManager;

    @BeforeEach
    public void setUp() {
        historyManager = new InMemoryHistoryManager();
    }

    @Test
    public void add() {
        Task task = new Task("Test addNewTask", "Test addNewTask description");
        historyManager.add(task);

        final List<Task> tasks = historyManager.getHistory();

        assertNotNull(tasks, "Задачи не возвращаются.");

        historyManager.remove(task.getId());


    }
}
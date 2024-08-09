package service;

import model.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


class HistoryManagerTest {

    private HistoryManager historyManager;

    @BeforeEach
    public void setUp() {
        historyManager = new InMemoryHistoryManager();
    }

    @Test
    public void testAddTask() {
        Task task = new Task("Test addNewTask", "Test addNewTask description");
        historyManager.add(task);
        final List<Task> tasks = historyManager.getHistory();
        assertNotNull(tasks, "Задачи не возвращаются.");
        assertEquals(1, historyManager.getHistory().size());

    }

    @Test
    public void testRemoveTask() {
        Task task = new Task("Test addNewTask", "Test addNewTask description");
        historyManager.add(task);
        final List<Task> tasks = historyManager.getHistory();
        tasks.remove(task.getId());
        assertEquals(0, tasks.size());
        assertFalse(tasks.contains(task));
    }
}



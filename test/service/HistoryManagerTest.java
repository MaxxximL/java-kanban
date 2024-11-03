package service;

import model.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class HistoryManagerTest {

    private HistoryManager historyManager;

    @BeforeEach
    public void setUp() {
        historyManager = new InMemoryHistoryManager();
    }

    @Test
    public void testEmptyHistory() {
        assertTrue(historyManager.getHistory().isEmpty(), "                          .");
    }

    @Test
    public void testAddTaskToHistory() {
        Task task = new Task("Task 1", "Description 1", Duration.ofMinutes(30), LocalDateTime.now());
        historyManager.add(task);

        List<Task> history = historyManager.getHistory();
        assertEquals(1, history.size());
        assertEquals(task, history.get(0), "                            .");
    }

    @Test
    public void testDuplicateTaskInHistory() {
        Task task = new Task("Task 1", "Description 1", Duration.ofMinutes(30), LocalDateTime.now());
        historyManager.add(task);
        historyManager.add(task);

        List<Task> history = historyManager.getHistory();
        assertEquals(1, history.size(), "                                      .");
        assertEquals(task, history.get(0), "                                                 .");
    }

}

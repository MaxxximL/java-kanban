package service;

import model.Epic;
import model.Status;
import model.SubTask;
import model.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class TaskManagerTest {

    private TaskManager taskManager;

    @BeforeEach
    public void setUp() {
        taskManager = new InMemoryTaskManager();
    }


    @Test
    public void testCreateTask() {
        Task task = new Task("Task 1", "Description 1", Duration.ofMinutes(30), LocalDateTime.now());
        Task createdTask = taskManager.createTask(task);
        assertNotNull(createdTask);
        assertEquals("Task 1", createdTask.getName());
        assertEquals(Status.NEW, createdTask.getStatus());
    }

    @Test
    public void testCreateEpic() {
        Epic epic = new Epic("Epic 1", "Description 1");
        Epic createdEpic = taskManager.createEpic(epic);
        assertNotNull(createdEpic, "Задача не найдена.");
        assertEquals(epic, createdEpic);

    }

    @Test
    public void testCreateSubTask() {
        Epic epic = new Epic("Epic 1", "Description 1");
        Epic createdEpic = taskManager.createEpic(epic);
        SubTask subTask = new SubTask("SubTask 1", "Description 1", createdEpic.getId());
        SubTask createdSubTask = taskManager.createSubTask(subTask);

        assertEquals(subTask, createdSubTask);
    }

    @Test
    public void testGetEpicById() {
        Epic epic = new Epic("Epic 1", "Description 1");
        Epic createdEpic = taskManager.createEpic(epic);
        Epic retrievedEpic = taskManager.getEpicById(createdEpic.getId());
        assertEquals(createdEpic, retrievedEpic);
    }


    @Test
    public void testGetAllEpics() {
        List<Epic> epics = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            epics.add(new Epic("Epic 1", "Description 1" + i));
            taskManager.createEpic(epics.get(i));
        }
        List<Epic> allEpics = taskManager.getAllEpics();
        assertEquals(5, allEpics.size());
        for (int i = 0; i < 5; i++) {
            assertEquals(epics.get(i), allEpics.get(i));
        }
    }


    @Test
    public void testUpdateEpic() {
        Epic epic = new Epic("Epic 1", "Description 1");
        Epic createdEpic = taskManager.createEpic(epic);
        Epic updatedEpic = new Epic("Updated Epic 1", "Updated Description 1");
        updatedEpic.setId(createdEpic.getId());
        taskManager.updateEpic(updatedEpic);
        assertEquals(updatedEpic, taskManager.getEpicById(createdEpic.getId()));
    }


    @Test
    public void testDeleteEpic() {
        Epic epic = new Epic("Epic 1", "Description 1");
        Epic createdEpic = taskManager.createEpic(epic);
        assertNotNull(taskManager.getEpicById(createdEpic.getId()));

        taskManager.deleteEpic(createdEpic.getId());
        assertNull(taskManager.getTaskById(createdEpic.getId()));
    }

    @Test
    public void testEpicStatusAllNew() {
        Epic epic = new Epic("Epic 1", "Description");
        taskManager.createEpic(epic);

        LocalDateTime now = LocalDateTime.now();
        SubTask subTask1 = new SubTask("SubTask 1", "Description 1", epic.getId(), Duration.ofMinutes(10), now);
        SubTask subTask2 = new SubTask("SubTask 2", "Description 2", epic.getId(), Duration.ofMinutes(15), now.plusMinutes(10));

        subTask1.setStatus(Status.NEW);
        subTask2.setStatus(Status.NEW);
        taskManager.createSubTask(subTask1);
        taskManager.createSubTask(subTask2);

        assertEquals(Status.NEW, epic.getStatus());
    }

    @Test
    public void testEpicStatusNewAndDone() {
        Epic epic = new Epic("Epic Test", "Description");
        taskManager.createEpic(epic);

        LocalDateTime now = LocalDateTime.now();
        SubTask subTask1 = new SubTask("SubTask 1", "Description 1", epic.getId(), Duration.ofMinutes(10), now);
        SubTask subTask2 = new SubTask("SubTask 2", "Description 2", epic.getId(), Duration.ofMinutes(15), now.plusMinutes(10));

        subTask1.setStatus(Status.NEW);
        subTask2.setStatus(Status.DONE);
        taskManager.createSubTask(subTask1);
        taskManager.createSubTask(subTask2);

        assertEquals(Status.IN_PROGRESS, epic.getStatus());
    }

    @Test
    public void testEpicStatusInProgress() {
        Epic epic = new Epic("Epic 1", "Description");
        taskManager.createEpic(epic);

        SubTask subTask1 = new SubTask("SubTask 1", "Description", epic.getId(), Duration.ofHours(1), LocalDateTime.now());
        subTask1.setStatus(Status.IN_PROGRESS);
        taskManager.createSubTask(subTask1);

        assertEquals(Status.IN_PROGRESS, epic.getStatus());
    }

    @Test
    public void testEpicStatusAllDone() {
        Epic epic = new Epic("Epic Test", "Description");
        taskManager.createEpic(epic);

        LocalDateTime now = LocalDateTime.now();
        SubTask subTask1 = new SubTask("SubTask 1", "Description 1", epic.getId(), Duration.ofMinutes(10), now);
        SubTask subTask2 = new SubTask("SubTask 2", "Description 2", epic.getId(), Duration.ofMinutes(15), now.plusMinutes(10));

        subTask1.setStatus(Status.DONE);
        subTask2.setStatus(Status.DONE);
        taskManager.createSubTask(subTask1);
        taskManager.createSubTask(subTask2);

        assertEquals(Status.DONE, epic.getStatus());
    }

    @Test
    public void testCreateSubTaskShouldAssociateWithEpic() {
        Epic epic = new Epic("Epic 1", "Description");
        taskManager.createEpic(epic);

        SubTask subTask = new SubTask("SubTask 1", "Description 1", epic.getId(), Duration.ofMinutes(30), LocalDateTime.now());
        SubTask createdSubTask = taskManager.createSubTask(subTask);

        assertEquals(epic.getId(), createdSubTask.getEpicId(), "Подзадача должна быть связана с эпиком.");
        assertTrue(epic.getSubTasks().contains(createdSubTask), "Эпик должен содержать подзадачу.");
    }

    @Test
    public void testEpicStatusUpdateOnSubTaskCreation() {
        Epic epic = new Epic("Epic 1", "Description");
        taskManager.createEpic(epic);

        LocalDateTime now = LocalDateTime.now();
        SubTask subTask1 = new SubTask("SubTask 1", "Description 1", epic.getId(), Duration.ofMinutes(10), now);
        SubTask subTask2 = new SubTask("SubTask 2", "Description 2", epic.getId(), Duration.ofMinutes(15), now.plusMinutes(10));

        subTask1.setStatus(Status.NEW);
        subTask2.setStatus(Status.DONE);
        taskManager.createSubTask(subTask1);
        taskManager.createSubTask(subTask2);

        assertEquals(Status.IN_PROGRESS, epic.getStatus(), "Статус эпика должен быть IN_PROGRESS после добавления подзадачи NEW.");
        assertEquals(Status.IN_PROGRESS, epic.getStatus(), "Статус эпика должен оставаться IN_PROGRESS если есть подзадачи NEW и DONE.");
    }


    @Test
    public void testCreateTaskWithOverlappingTime() {
        Task task1 = new Task("Task 1", "Description 1", Duration.ofMinutes(30), LocalDateTime.now());
        taskManager.createTask(task1);

        Task task2 = new Task("Task 2", "Description 2", Duration.ofMinutes(30), LocalDateTime.now().plusMinutes(15)); // Пересечение
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            taskManager.createTask(task2);
        });

        assertEquals("Задача пересекается с существующими задачами.", exception.getMessage());
    }

    @Test
    public void testCreateSubTaskWithOverlappingTime() {
        Epic epic = new Epic("Epic 1", "Description");
        taskManager.createEpic(epic);

        Task task1 = new Task("Task 1", "Description 1", Duration.ofMinutes(30), LocalDateTime.now());
        taskManager.createTask(task1);

        SubTask subTask = new SubTask("SubTask 1", "Description 1", epic.getId(), Duration.ofMinutes(30), LocalDateTime.now().plusMinutes(15)); // Пересечение
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            taskManager.createSubTask(subTask);
        });

        assertEquals("Подзадача пересекается с существующими задачами или подзадачами.", exception.getMessage());
    }
}



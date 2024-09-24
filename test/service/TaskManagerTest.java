package service;

import model.Epic;
import model.SubTask;
import model.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
    public void addNewTask() {
        Task task = new Task("Test addNewTask", "Test addNewTask description");
        taskManager.createTask(task);

        final Task savedTask = taskManager.getTaskById(task.getId());

        assertNotNull(savedTask, "Задача не найдена.");
        assertEquals(task, savedTask, "Задачи не совпадают.");

        final List<Task> tasks = taskManager.getAllTasks();

        assertNotNull(tasks, "Задачи не возвращаются.");
        assertEquals(1, tasks.size(), "Неверное количество задач.");
        assertEquals(task, tasks.get(0), "Задачи не совпадают.");

    }

    @Test
    public void testUpdateTask() {
        Task task = new Task("Test addNewTask", "Test addNewTask description");
        Task createdTask = taskManager.createTask(task);
        createdTask.setName("Updated Task");
        taskManager.updateTask(createdTask);
        assertEquals("Updated Task", taskManager.getTaskById(createdTask.getId()).getName());

    }

    @Test
    public void testDeleteTask() {
        Task task = new Task("Test addNewTask", "Test addNewTask description");
        Task createdTask = taskManager.createTask(task);
        assertNotNull(taskManager.getTaskById(createdTask.getId()));

        taskManager.deleteTask(createdTask.getId());
        assertNull(taskManager.getTaskById(createdTask.getId()));
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
        SubTask subTask = new SubTask("SubTask 1", "Description 1", epic.getId());
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
    public void testGetSubTaskById() {
        Epic epic = new Epic("Epic 1", "Description 1");
        SubTask subTask = new SubTask("SubTask 1", "Description 1", epic.getId());
        SubTask createdSubTask = taskManager.createSubTask(subTask);
        SubTask retrievedSubTask = taskManager.getSubTaskById(createdSubTask.getId());
        assertEquals(createdSubTask, retrievedSubTask);
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
    public void testGetAllSubTasks() {
        List<SubTask> subTasks = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            subTasks.add(new SubTask("SubTask 1", "Description 1", null));
            taskManager.createSubTask(subTasks.get(i));
        }
        List<SubTask> allSubTasks = taskManager.getAllSubTasks();
        assertEquals(5, allSubTasks.size());
        for (int i = 0; i < 5; i++) {
            assertEquals(subTasks.get(i), allSubTasks.get(i));
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
    public void testUpdateSubTask() {
        Epic epic = new Epic("Epic 1", "Description 1");
        SubTask subtask = new SubTask("Subtask 1", "Description 1", epic.getId());
        SubTask createdSubtask = taskManager.createSubTask(subtask);
        SubTask updatedSubtask = new SubTask("Updated Subtask 1", "Updated Description 1", epic.getId());
        updatedSubtask.setId(createdSubtask.getId());
        taskManager.updateSubTask(updatedSubtask);
        assertEquals(updatedSubtask, taskManager.getSubTaskById(createdSubtask.getId()));
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
    public void testDeleteSubTask() {
        Epic epic = new Epic("Epic 1", "Description 1");
        SubTask subTask = new SubTask("SubTask 1", "Description 1", epic.getId());
        SubTask createdSubTask = taskManager.createSubTask(subTask);
        assertNotNull(taskManager.getSubTaskById(createdSubTask.getId()));

        taskManager.deleteSubTask(createdSubTask.getId());
        assertNull(taskManager.getTaskById(createdSubTask.getId()));
    }
}


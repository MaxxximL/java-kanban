package server;

import com.google.gson.Gson;
import model.Epic;
import model.Status;
import model.SubTask;
import model.Task;
import org.junit.jupiter.api.*;
import service.Managers;
import service.TaskManager;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.LocalDateTime;


import static org.junit.jupiter.api.Assertions.*;

public class HttpTaskManagerTasksTest {

    private static final TaskManager manager = Managers.getDefault();
    private static HttpTaskServer taskServer;
    private final Gson gson = Managers.getGson();

    @BeforeAll
    public static void beforeAll() throws IOException {
        taskServer = new HttpTaskServer(manager);
        taskServer.start();
    }

    @BeforeEach
    void init() {
        manager.deleteTasks();
        manager.deleteSubtasks();
        manager.deleteEpics();
    }

    @AfterAll
    public static void afterAll() {
        taskServer.stop();
    }




    @Test
    public void testGetEpicById() throws IOException, InterruptedException {
        Epic epic = new Epic("Epic to get", "Epic description");
        manager.createEpic(epic);

        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/epics/" + epic.getId());

        HttpRequest request = HttpRequest.newBuilder().uri(url).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response.statusCode());
        Epic retrievedEpic = gson.fromJson(response.body(), Epic.class);

        assertNotNull(retrievedEpic, "Epic should not be null");
        assertEquals(epic.getId(), retrievedEpic.getId(), "Epic ID should match");
    }

    @Test
    public void testDeleteEpic() throws IOException, InterruptedException {
        Epic epic = new Epic("Epic to delete", "Epic description");
        manager.createEpic(epic);

        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/epics/" + epic.getId());

        HttpRequest request = HttpRequest.newBuilder().uri(url).DELETE().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response.statusCode());


        assertNull(manager.getEpicById(epic.getId()), "Epic should be deleted and return null");
    }


    @Test
    public void testDeleteSubTask() throws IOException, InterruptedException {
        Epic dummyEpic = new Epic("Epic for delete", "Description");
        int epicId = manager.createEpic(dummyEpic).getId();

        SubTask subTask = new SubTask("Subtask to delete", "Description", epicId);
        manager.createSubTask(subTask);

        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/subtasks/" + subTask.getId());

        HttpRequest request = HttpRequest.newBuilder().uri(url).DELETE().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response.statusCode());


        assertNull(manager.getSubTaskById(subTask.getId()), "SubTask should be deleted and return null");
    }

    @Test
    public void testAddTask() {
        Task task = new Task("New Task", "Task description", Duration.ofMinutes(30), LocalDateTime.now());
        Task createdTask = manager.createTask(task);

        assertNotNull(createdTask, "Task should be created");
        assertEquals("New Task", createdTask.getName(), "Task name should match");
        assertEquals(Status.NEW, createdTask.getStatus(), "Task status should be NEW");
    }

    @Test
    public void testUpdateTask() {
        Task task = new Task("Old Task", "Task description", Duration.ofMinutes(30), LocalDateTime.now());
        Task createdTask = manager.createTask(task);

        Task updatedTask = new Task(createdTask.getId(), "Updated Task", "Updated description", Duration.ofMinutes(60), LocalDateTime.now());
        manager.updateTask(updatedTask);

        Task fetchedTask = manager.getTaskById(createdTask.getId());
        assertEquals("Updated Task", fetchedTask.getName(), "Task name should be updated");
        assertEquals("Updated description", fetchedTask.getDescription(), "Task description should be updated");
    }

    @Test
    public void testAddSubtask() {
        Epic epic = new Epic("New Epic", "Epic description");
        Epic createdEpic = manager.createEpic(epic);
        SubTask subTask = new SubTask("New SubTask", "SubTask description", createdEpic.getId(), Duration.ofMinutes(15), LocalDateTime.now());
        SubTask createdSubTask = manager.createSubTask(subTask);

        assertNotNull(createdSubTask, "SubTask should be created");
        assertEquals(createdEpic.getId(), createdSubTask.getEpicId(), "SubTask should be associated with the correct Epic");
        assertTrue(createdEpic.getSubTasks().contains(createdSubTask), "Epic should contain the created SubTask");
    }


    @Test
    public void testAddEpic() {
        Epic epic = new Epic("New Epic", "Epic description");
        Epic createdEpic = manager.createEpic(epic);

        assertNotNull(createdEpic, "Epic should be created");
        assertEquals("New Epic", createdEpic.getName(), "Epic name should match");
        assertEquals(Status.NEW, createdEpic.getStatus(), "Epic status should be NEW");
    }

    @Test
    public void testGetSubtaskById() {
        Epic epic = new Epic("Get SubTask Epic", "Epic description");
        Epic createdEpic = manager.createEpic(epic);
        SubTask subTask = new SubTask("Get SubTask", "SubTask description", createdEpic.getId(), Duration.ofMinutes(15), LocalDateTime.now());
        SubTask createdSubTask = manager.createSubTask(subTask);

        SubTask fetchedSubTask = manager.getSubTaskById(createdSubTask.getId());
        assertNotNull(fetchedSubTask, "SubTask should be fetched");
        assertEquals(createdSubTask.getId(), fetchedSubTask.getId(), "Fetched SubTask ID should match");
    }

}

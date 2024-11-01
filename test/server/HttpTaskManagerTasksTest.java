package server;

import com.google.gson.Gson;
import model.Epic;
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
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class HttpTaskManagerTasksTest {

    private static final TaskManager manager = Managers.getDefault();
    private static HttpTaskServer taskServer;
    private final Gson gson = Managers.getGson();


    @BeforeEach
    public void setUp() {
        taskServer = new HttpTaskServer(manager);
        manager.deleteTasks();
        manager.deleteSubtasks();
        manager.deleteEpics();
        taskServer.start();
    }

    @AfterEach
    public void shutDown() {
        taskServer.stop();
    }


    @Test
    public void testAddTask() throws IOException, InterruptedException {
        final Task task = new Task("Task to create title", "Task to create description");
        String taskJson = gson.toJson(task);

        HttpClient client = HttpClient.newHttpClient();


        URI url = URI.create("http://localhost:8080/tasks");
        HttpRequest request = HttpRequest.newBuilder().uri(url)
                .POST(HttpRequest.BodyPublishers.ofString(taskJson))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(201, response.statusCode());




        List<Task> tasksFromManager = manager.getAllTasks();
        assertNotNull(tasksFromManager, "No tasks returned from manager");
        assertEquals(1, tasksFromManager.size(), "Incorrect count of tasks");
        assertEquals("Task to create title", tasksFromManager.get(0).getTitle(),
                "Incorrect task title");
    }

    @Test
    public void testUpdateTask() throws IOException, InterruptedException {
        final Task task = new Task("Task to update title", "Task to update description");
        manager.createTask(task);
        task.setTitle("Task updated!");
        String taskJson = gson.toJson(task);

        HttpClient client = HttpClient.newHttpClient();

        URI url = URI.create("http://localhost:8080/tasks");
        HttpRequest request = HttpRequest.newBuilder().uri(url)
                .POST(HttpRequest.BodyPublishers.ofString(taskJson))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(201, response.statusCode());


        List<Task> tasksFromManager = manager.getTasks();
        assertNotNull(tasksFromManager, "No tasks returned from manager");
        assertEquals(1, tasksFromManager.size(), "Incorrect count of tasks");
        assertEquals("Task updated!", tasksFromManager.get(0).getTitle(), "Incorrect update title");
    }

    @Test
    public void testAddSubtask() throws IOException, InterruptedException {
        Epic dummyEpic = new Epic("Epic title", "Epic description");
        int epicId = manager.addNewEpic(dummyEpic);


        SubTask subtask = new SubTask("Subtask to create title", "Subtask to create description", epicId);
        String subtaskJson = gson.toJson(subtask);

        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/subtasks");
        HttpRequest request = HttpRequest.newBuilder().uri(url)
                .POST(HttpRequest.BodyPublishers.ofString(subtaskJson))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(201, response.statusCode());


        List<SubTask> subtasksFromManager = manager.getAllSubTasks();
        assertNotNull(subtasksFromManager, "No subtasks returned from manager");
        assertEquals(1, subtasksFromManager.size(), "Incorrect count of subtasks");
        assertEquals("Subtask to create title", subtasksFromManager.get(0).getTitle(),
                "Incorrect subtask title");
    }

    @Test
    public void testUpdateSubtask() throws IOException, InterruptedException {
        Epic dummyEpic = new Epic("Epic title", "Epic description");
        int epicId = manager.addNewEpic(dummyEpic);

        final SubTask subtask;
        subtask = new SubTask("Subtask to update title", "Subtask to update description", epicId);

        manager.createSubTask(subtask);
        subtask.setTitle("Subtask updated!");
        String subtaskJson = gson.toJson(subtask);

        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/subtasks");
        HttpRequest request = HttpRequest.newBuilder().uri(url)
                .POST(HttpRequest.BodyPublishers.ofString(subtaskJson))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(201, response.statusCode());


        List<SubTask> subtasksFromManager = manager.getAllSubTasks();
        assertNotNull(subtasksFromManager, "No subtasks returned from manager");
        assertEquals(1, subtasksFromManager.size(), "Incorrect count of subtasks");
        assertEquals("Subtask updated!", subtasksFromManager.get(0).getTitle(),
                "Incorrect subtask title");
    }

    @Test
    public void testAddEpic() throws IOException, InterruptedException {
        Epic epic = new Epic("Epic to create title", "Epic to create description");
        String epicJson = gson.toJson(epic);

        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/epics");
        HttpRequest request = HttpRequest.newBuilder().uri(url)
                .POST(HttpRequest.BodyPublishers.ofString(epicJson))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(201, response.statusCode());


        List<Epic> epicsFromManager = manager.getAllEpics();
        assertNotNull(epicsFromManager, "No epics returned from manager");
        assertEquals(1, epicsFromManager.size(), "Incorrect count of epics");
        assertEquals("Epic to create title", epicsFromManager.get(0).getTitle(),
                "Incorrect epic title");

    }
}

package server;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import model.Task;
import service.TaskManager;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class TaskHandler extends BaseHttpHandler {
    private final TaskManager taskManager;
    private final Gson gson = new Gson();

    public TaskHandler(TaskManager taskManager) {
        this.taskManager = taskManager;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        switch (exchange.getRequestMethod()) {
            case "GET":
                if (exchange.getRequestURI().getPath().endsWith("/tasks")) {
                    sendText(exchange, gson.toJson(taskManager.getAllTasks()), 200);
                } else {
                    int id = Integer.parseInt(exchange.getRequestURI().getPath().split("/")[2]);
                    Task task = taskManager.getTaskById(id);
                    if (task != null) {
                        sendText(exchange, gson.toJson(task), 200);
                    } else {
                        sendNotFound(exchange);
                    }
                }
                break;
            case "POST":
                InputStream body = exchange.getRequestBody();
                String requestBody = new String(body.readAllBytes(), StandardCharsets.UTF_8);
                Task task = gson.fromJson(requestBody, Task.class);
                try {
                    if (task.getId() == 0) {
                        taskManager.createTask(task);
                        sendText(exchange, gson.toJson(task), 201);
                    } else {
                        taskManager.updateTask(task);
                        sendText(exchange, gson.toJson(task), 200);
                    }
                } catch (IllegalArgumentException e) {
                    sendHasInteractions(exchange);
                }
                break;
            case "DELETE":
                int id = Integer.parseInt(exchange.getRequestURI().getPath().split("/")[2]);
                taskManager.deleteTask(id);
                sendText(exchange, "Task deleted", 200);
                break;
            default:
                sendNotFound(exchange);
                break;
        }
    }
}

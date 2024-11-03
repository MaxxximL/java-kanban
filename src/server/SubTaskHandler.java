package server;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import model.SubTask;
import service.TaskManager;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class SubTaskHandler extends BaseHttpHandler {
    private final TaskManager taskManager;
    private Gson gson = new Gson();

    public SubTaskHandler(TaskManager taskManager, Gson gson) {
        this.taskManager = taskManager;
        this.gson = gson;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        switch (exchange.getRequestMethod()) {
            case "GET":
                if (exchange.getRequestURI().getPath().endsWith("/subtasks")) {
                    sendText(exchange, gson.toJson(taskManager.getAllSubTasks()), 200);
                } else {
                    int id = Integer.parseInt(exchange.getRequestURI().getPath().split("/")[2]);
                    SubTask subTask = taskManager.getSubTaskById(id);
                    if (subTask != null) {
                        sendText(exchange, gson.toJson(subTask), 200);
                    } else {
                        sendNotFound(exchange);
                    }
                }
                break;
            case "POST":
                InputStream body = exchange.getRequestBody();
                String requestBody = new String(body.readAllBytes(), StandardCharsets.UTF_8);
                SubTask subTask = gson.fromJson(requestBody, SubTask.class);
                try {
                    if (subTask.getId() == 0) {
                        taskManager.createSubTask(subTask);
                        sendText(exchange, gson.toJson(subTask), 201);
                    } else {
                        taskManager.updateSubTask(subTask);
                        sendText(exchange, gson.toJson(subTask), 200);
                    }
                } catch (IllegalArgumentException e) {
                    sendHasInteractions(exchange);
                }
                break;
            case "DELETE":
                int id = Integer.parseInt(exchange.getRequestURI().getPath().split("/")[2]);
                taskManager.deleteSubTask(id);
                sendText(exchange, "SubTask deleted", 200);
                break;
            default:
                sendNotFound(exchange);
                break;
        }
    }
}

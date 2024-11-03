package server;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import model.Epic;
import service.TaskManager;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

public class EpicHandler extends BaseHttpHandler {
    private final TaskManager taskManager;
    private final Gson gson = new Gson();

    public EpicHandler(TaskManager taskManager, Gson gson) {
        this.taskManager = taskManager;
    }

    private void sendServerError(HttpExchange exchange, String message) {
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            switch (exchange.getRequestMethod()) {
                case "GET":
                    handleGet(exchange);
                    break;
                case "POST":
                    handlePost(exchange);
                    break;
                case "DELETE":
                    handleDelete(exchange);
                    break;
                default:
                    sendNotFound(exchange);
                    break;
            }
        } catch (Exception e) {
            sendServerError(exchange, e.getMessage());
        }
    }


    private void handleGet(HttpExchange exchange) throws IOException {
        String path = exchange.getRequestURI().getPath();
        if (path.endsWith("/epics")) {
            sendText(exchange, gson.toJson(taskManager.getAllEpics()), 200);
        } else {
            int id = Integer.parseInt(path.split("/")[2]);
            Optional<Epic> epicOptional = Optional.ofNullable(taskManager.getEpicById(id));
            epicOptional.ifPresentOrElse(
                    epic -> {
                        try {
                            sendText(exchange, gson.toJson(epic), 200);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    },
                    () -> {
                        try {
                            sendNotFound(exchange);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
            );
        }
    }

    private void handlePost(HttpExchange exchange) throws IOException {
        String requestBody = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
        Epic epic = gson.fromJson(requestBody, Epic.class);

        try {
            if (epic.getId() == 0) {
                taskManager.createEpic(epic);
                sendText(exchange, gson.toJson(epic), 201);
            } else {
                taskManager.updateEpic(epic);
                sendText(exchange, gson.toJson(epic), 200);
            }
        } catch (IllegalArgumentException e) {
            sendHasInteractions(exchange);
        }
    }

    private void handleDelete(HttpExchange exchange) throws IOException {
        int id = Integer.parseInt(exchange.getRequestURI().getPath().split("/")[2]);
        taskManager.deleteEpic(id);
        sendText(exchange, "Epic deleted", 200);
    }
}
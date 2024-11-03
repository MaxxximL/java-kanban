package server;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import service.TaskManager;

import java.io.IOException;

public class PrioritizedHandler extends BaseHttpHandler {
    private final TaskManager taskManager;
    private Gson gson = new Gson();

    public PrioritizedHandler(TaskManager taskManager, Gson gson) {
        this.taskManager = taskManager;
        this.gson = gson;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        switch (exchange.getRequestMethod()) {
            case "GET":
                sendText(exchange, gson.toJson(taskManager.getPrioritizedTasks()), 200);
                break;
            default:
                sendNotFound(exchange);
                break;
        }
    }
}

package server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpServer;
import service.Managers;
import service.TaskManager;
import java.time.*;

import java.io.IOException;
import java.net.InetSocketAddress;

public class HttpTaskServer {
    private final TaskManager taskManager;

    public HttpTaskServer(TaskManager taskManager) {
        this.taskManager = taskManager;
    }

    public static void main(String[] args) {
        TaskManager taskManager = Managers.getDefault();
        HttpTaskServer server = new HttpTaskServer(taskManager);
        server.start();
    }

    public static Gson getGson() {
        return new GsonBuilder()
                .registerTypeAdapter(Duration.class, new DurationAdapter())
                .setPrettyPrinting()
                .excludeFieldsWithoutExposeAnnotation()
                .create();
    }

    public void start() {
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
            server.createContext("/tasks", new TaskHandler(taskManager));
            server.createContext("/subtasks", new SubTaskHandler(taskManager));
            server.createContext("/epics", new EpicHandler(taskManager));
            server.createContext("/history", new HistoryHandler(taskManager));
            server.createContext("/prioritized", new PrioritizedHandler(taskManager));
            server.setExecutor(null);
            server.start();
            System.out.println("Server is started on port 8080");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stop() {
   }
}

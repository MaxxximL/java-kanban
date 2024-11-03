package server;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpServer;
import service.Managers;
import service.TaskManager;

import java.io.IOException;
import java.net.InetSocketAddress;


public class HttpTaskServer {
    private final TaskManager taskManager;
    private final Gson gson;
    private HttpServer server;

    public HttpTaskServer(TaskManager taskManager) {
        this.taskManager = taskManager;
        this.gson = Managers.getGson();


    }

    public static void main(String[] args) {
        TaskManager taskManager = Managers.getDefault();
        HttpTaskServer server = new HttpTaskServer(taskManager);
        server.start();
    }

    public void start() {
        try {
            server = HttpServer.create(new InetSocketAddress(8080), 0);
            server.createContext("/tasks", new TaskHandler(taskManager, gson));
            server.createContext("/subtasks", new SubTaskHandler(taskManager, gson));
            server.createContext("/epics", new EpicHandler(taskManager, gson));
            server.createContext("/history", new HistoryHandler(taskManager, gson));
            server.createContext("/prioritized", new PrioritizedHandler(taskManager, gson));
            server.setExecutor(null);
            server.start();
            System.out.println("Server is started on port 8080");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        if (server != null) {
            server.stop(0);
            System.out.println("Server stopped.");
        }
    }
}
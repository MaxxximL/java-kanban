package service;

import model.Task;

import java.util.List;


public class Main {

    public static void main(String[] args) {
        TaskManager taskManager = Managers.getDefault();

        for (int i = 1; i < 20; i++) {
            taskManager.createTask(new Task("name" + i, "desc" + i));
        }

        for (int i = 5; i < 18; i++) {
            taskManager.getTaskById(i);
        }

        List<Task> history = taskManager.getHistory();
        System.out.println(history);

    }
}
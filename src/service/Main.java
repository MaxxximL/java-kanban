package service;

import model.Epic;
import model.Status;
import model.SubTask;
import model.Task;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class Main {

    public static void main(String[] args) {
        TaskManager taskManager = Managers.getDefault();

        Task task = taskManager.createTask(new Task("Новая задача", "Описание"));
        System.out.println("Создана задача: " + task);

        Epic epic = taskManager.createEpic(new Epic("Новый эпик", "Описание"));
        System.out.println("Создан эпик: " + epic);

        SubTask subTask1 = taskManager.createSubTask(new SubTask("Подзадача 1", "Описание", epic));
        System.out.println("Создана подзадача: " + subTask1);

        SubTask subTask2 = taskManager.createSubTask(new SubTask("Подзадача 2", "Описание", epic));
        System.out.println("Создана подзадача: " + subTask2);


        System.out.println("Эпик после добавления подзадач: " + epic);

        subTask1.setStatus(Status.IN_PROGRESS);
        taskManager.updateSubTask(subTask1);
        System.out.println("Эпик после обновления статуса подзадачи: " + epic);

        subTask2.setStatus(Status.DONE);
        taskManager.updateSubTask(subTask2);
        System.out.println("Эпик после обновления статуса подзадачи: " + epic);

        subTask1.setStatus(Status.DONE);
        taskManager.updateSubTask(subTask1);
        System.out.println("Эпик после завершения всех подзадач: " + epic);

        System.out.println("История просмотров:");
        for (Task t : taskManager.getHistory()) {
            System.out.println(t);


        }

    }
}




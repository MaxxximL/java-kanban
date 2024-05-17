
//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package service;

import model.Epic;
import model.Status;
import model.SubTask;
import model.Task;

public class Main {
    public Main() {
    }

    public static void main(String[] args) {
        TaskManager taskManager = new TaskManager();
        Task task = taskManager.createTask(new Task("Новая задача", "Описание"));
        System.out.println("Создана задача: " + String.valueOf(task));
        Epic epic = taskManager.createEpic(new Epic("Новый эпик", "Описание"));
        System.out.println("Создан эпик: " + String.valueOf(epic));
        SubTask subTask1 = taskManager.createSubTask(new SubTask("Подзадача 1", "Описание", epic));
        System.out.println("Создана подзадача: " + String.valueOf(subTask1));
        SubTask subTask2 = taskManager.createSubTask(new SubTask("Подзадача 2", "Описание", epic));
        System.out.println("Создана подзадача: " + String.valueOf(subTask2));
        System.out.println("Эпик после добавления подзадач: " + String.valueOf(epic));
        subTask1.setStatus(Status.IN_PROGRESS);
        taskManager.updateSubTask(subTask1);
        System.out.println("Эпик после обновления статуса подзадачи: " + String.valueOf(epic));
        subTask2.setStatus(Status.DONE);
        taskManager.updateSubTask(subTask2);
        System.out.println("Эпик после обновления статуса подзадачи: " + String.valueOf(epic));
        subTask1.setStatus(Status.DONE);
        taskManager.updateSubTask(subTask1);
        System.out.println("Эпик после завершения всех подзадач: " + String.valueOf(epic));
    }
}


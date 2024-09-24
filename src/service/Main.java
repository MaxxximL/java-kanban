package service;

import model.Epic;
import model.SubTask;
import model.Task;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;


public class Main {

    public static void main(String[] args) {


        Path file = Paths.get("path/to/file");
        FileBackedTaskManager taskManager = new FileBackedTaskManager(file);

        Task task1 = new Task("Task 1", "Description 1");
        Task task2 = new Task("Task 2", "Description 2");
        Epic epic1 = new Epic("Epic 1", "Description 1");
        SubTask subTask1 = new SubTask("SubTask 1", "Description 1", epic1.getId());
        SubTask subTask2 = new SubTask("SubTask 2", "Description 2", epic1.getId());

        taskManager.createTask(task1);
        taskManager.createTask(task2);
        taskManager.createEpic(epic1);
        taskManager.createSubTask(subTask1);
        taskManager.createSubTask(subTask2);


        taskManager.save();

        FileBackedTaskManager loadedTaskManager = FileBackedTaskManager.loadFromFile();
        List<Task> tasks = loadedTaskManager.getAllTasks();
        System.out.println(tasks);

        List<Epic> epics = loadedTaskManager.getAllEpics();
        System.out.println(epics);

        List<SubTask> subTasks = loadedTaskManager.getAllSubTasks();
        System.out.println(subTasks);


    }
}
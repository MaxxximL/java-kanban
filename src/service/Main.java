
package service;

import model.Epic;
import model.Task;
import model.SubTask;
import model.Status;

import java.util.ArrayList;


public class Main {



    public static void main(String[] args) {

        TaskManager taskManager = new TaskManager();



        Task task = taskManager.create(new Task(4, "Новая задача", Status.NEW, "Описание"));

        System.out.println("Create task: " + task);



        Task task1 = taskManager.create(new Task(10, "Новая задача", Status.NEW, "Описание"));

        System.out.println("Create task: " + task1);


        Task taskFromManager = taskManager.get(task.getId());
        System.out.println("Get task: " + taskFromManager);

        taskFromManager.setName("New name");
        taskFromManager.setId(8);
        taskFromManager.setStatus(Status.DONE);

        taskManager.update(taskFromManager);
        System.out.println("Update task: " + taskFromManager);


        Status status = task.getStatus(Status.DONE);
        System.out.println(status);

        taskManager.delete(taskFromManager.getId());
        System.out.println("Delete " + taskFromManager);

        ArrayList<Task> taskArrayList = taskManager.getAll();
        System.out.println("Список всех задач: " + taskArrayList);





        Epic epic = taskManager.createEpic(new Epic("Новый эпик","Описание"));
        System.out.println("Create epic " + epic);



        Task epicFromManager = taskManager.getEpicId(epic.getId());
        System.out.println("Get epic: " + epicFromManager);

        SubTask.setEpic(epic);
        System.out.println("SubTask SetEpic" + epic);


        epicFromManager.setName("EPIQUE");

        epic.setId(5);

        epic.updateStatus();


        taskManager.updateEpic(epic);
        System.out.println("UpdateEpic: " + epic);


        ArrayList<Epic> epicArrayList = taskManager.getAllEpics();
        System.out.println("Список всех эпиков: " + epicArrayList);





        SubTask subTask = taskManager.createSubTask(new SubTask(9,"Новая подзадача", Status.NEW, "Описание подзадачи", epic));
        System.out.println("Create subTask " + subTask);



        subTask.updateStatus();

        taskManager.updateSubTask(new SubTask(subTask.setId(99),"Новая подзадача", Status.IN_PROGRESS, "Описание подзадачи", epic)); ///
        System.out.println("Update SubTask " + subTask);






        epic.removeSubTask(subTask);
        System.out.println("Delete subTask " + subTask);


        ArrayList<SubTask> subTaskArrayList = epic.getSubTasks();
        System.out.println("Список всех сабтасков: " + subTaskArrayList);



        taskManager.deleteEpic(epic.setId(0));
        System.out.println("Delete Epic " + epic);



    }
}


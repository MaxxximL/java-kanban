
package service;

import model.Epic;
import model.Task;
import model.SubTask;
import model.Status;
import java.util.ArrayList;


public class Main {

    public static void main(String[] args) {

        TaskManager taskTracker  = new TaskManager();


        Epic epic = taskTracker.createEpic(new Epic("Эпик 1",  "Описание"));
        System.out.println("Create epic " + epic);


        SubTask subTask1 = new SubTask(taskTracker.generateId(), "Сабтаск 1", Status.NEW, "Описание", epic);


        taskTracker.createSubTask(subTask1);


        SubTask subTask2 = new SubTask(taskTracker.generateId(), "Сабтаск 2", Status.NEW, "Описание", epic);


        taskTracker.createSubTask(subTask2);


        System.out.println(epic);




        subTask2.setStatus(Status.IN_PROGRESS);

        taskTracker.updateSubTask(subTask1, Status.IN_PROGRESS, subTask2);

        System.out.println(epic);



        subTask1.setStatus(Status.DONE);

        taskTracker.updateSubTask(subTask1, Status.DONE, subTask2);


        System.out.println(epic);





        taskTracker.deleteEpic(epic.setId(0));
        System.out.println("Delete Epic " + epic);





    }
}


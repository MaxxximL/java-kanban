package model;
import model.Task;

import java.util.ArrayList;

import java.util.Objects;

public class Epic extends Task {

public  ArrayList<SubTask> subTasks = new ArrayList<>(); // Set

    private int seq = 0;

public Epic(String name, String description) {

    super(name, Status.IN_PROGRESS, description);

}


public ArrayList<SubTask> getSubTasks() {

    return subTasks;
}

public void addTask(SubTask subTask) {
    subTasks.add(subTask);

}

public void removeSubTask(SubTask subTask) {     // Set

    subTasks.remove(subTask);

}


    public int setId(int id) {

        return id;
    }

public void updateStatus() {

    status = Status.NEW;

}



}

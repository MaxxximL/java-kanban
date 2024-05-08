package model;
import model.Task;
import model.Status;
import java.util.ArrayList;

import java.util.Objects;

public class Epic extends Task {



    private ArrayList<SubTask> subTasks = new ArrayList<>(); // Set

    private int seq = 0;

    public Epic(String name, String description) {

        super(name,  description);

    }



    public ArrayList<SubTask> getSubTasks() {

        return subTasks;
    }

    public void setSubTasks(SubTask subTask) {
        subTasks.add(subTask);

    }

    public void removeSubTask(SubTask subTask) {     // Set

        subTasks.remove(subTask);

    }


    public int setId(int id) {

        return id;
    }

}

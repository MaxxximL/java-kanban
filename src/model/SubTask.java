package model;

import model.Epic;
import service.TaskManager;

import java.util.Objects;

public class SubTask extends Task {

    private Epic epic;

    public SubTask(String name,  String description) {

        super(name,  description);

    }

    public SubTask(int id, String name, Status status, String description, Epic epic) {

        super(id, name, status, description);

        this.epic = epic;

    }



    @Override
    public Epic getEpic() {

        return epic;

    }
    public static Epic setEpic (Epic epic){

        return epic;

    }

}



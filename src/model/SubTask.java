package model;

public class SubTask extends Task {


    private String epicId;

    public SubTask(String name, String description, String epicId) {
        super(name, description);
        this.epicId = epicId;
    }

    public String getEpic() {
        return epicId;
    }

    @Override
    public String getEpicId() {

        return epicId;
    }

    public void setEpic(String epicId) {
        this.epicId = epicId;
    }
}

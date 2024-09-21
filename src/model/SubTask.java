package model;

public class SubTask extends Task {

    private Epic epic;
    private Integer epicId;

    public SubTask(String name, String description, Epic epic) {
        super(name, description);
        this.epic = epic;
    }

    public Epic getEpic() {
        return epic;
    }

    @Override
    public Integer getEpicId() {

        return epicId;
    }

    public void setEpic(Epic epic) {
        this.epic = epic;
    }
}

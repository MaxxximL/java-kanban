package model;

public class SubTask extends Task {


    private Integer epicId;

    public SubTask(String name, String description, Integer epicId) {
        super(name, description);
        this.epicId = epicId;
    }

    public Integer getEpic() {
        return epicId;
    }

    @Override
    public Integer getEpicId() {

        return epicId;
    }

    public void setEpic(Integer epicId) {
        this.epicId = epicId;
    }
}

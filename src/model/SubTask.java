package model;

public class SubTask extends Task {

    private int epicId;

    public SubTask(String name, String description, int epicId) {
        super(name, description);
        this.epicId = epicId;
    }

    public SubTask(int id, String name, Status status, String description, int epicId) {
        super(id, name, status, description, epicId);
        this.epicId = epicId;
    }

    @Override
    public TaskType getType() {
        return TaskType.SUBTASK;
    }

    public int getEpic() {
        return epicId;
    }

    public void setEpic(int epicId) {
        this.epicId = epicId;
    }

    public int getEpicId() {

        return epicId;
    }
}
package model;

public class SubTask extends Task {

    private int epicId;
    private Epic epic;

    public SubTask(String name, String description, int epicId) {
        super(name, description);
        this.epicId = epicId;
    }

    public SubTask(String name, String description, Epic epic) {
        super(name, description);
        this.epic = epic;
    }

    public SubTask(int id, String name, Status status, String description, int epicId) {
        super(id, name, status, description, epicId);
        this.epicId = epicId;
    }

    @Override
    public TaskType getType() {
        return TaskType.SUBTASK;
    }

    public Epic getEpic() {
        return epic;
    }

    public void setEpic(Epic epic) {
        this.epic = epic;
    }

    public int getEpicId() {

        return epicId;
    }
}
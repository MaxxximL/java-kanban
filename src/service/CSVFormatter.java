package service;

import model.Status;
import model.Task;

public class CSVFormatter {

    private CSVFormatter() {

    }

    public static String toString(Task task) {

        return new StringBuilder()
                .append(task.getId()).append(",")
                .append(task.getName()).append(",")
                .append(task.getStatus()).append(",")
                .append(task.getDescription()).append(",")
                .append(task.getEpicId())
                .toString();
    }

    public static Task fromString(String value) {
        String[] parts = value.split(",");
        int id;
        try {
            id = Integer.parseInt(parts[0]);
        } catch (NumberFormatException e) {
            // handle the error, for example by setting the id to 0
            id = 0;
        }

        String name = parts[1];
        Status status = Status.valueOf(parts[2]);
        String description = parts[3];
        int epicId = Integer.parseInt(parts[4]);
        return new Task(id, name, status, description, epicId);
    }

    public static String getHeader() {

        return "id,name,status,description,epicId";
    }
}
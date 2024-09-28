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
        String id = parts[0];
        String name = parts[1];
        Status status = null;
        try {
            status = Status.valueOf(parts[2]);
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка статуса");
        }
        String description = parts[3];
        String epicId = parts[4]; // change to string
        return new Task(Integer.parseInt(id), name, status, description, Integer.parseInt(epicId));
    }

    public static String getHeader() {

        return "id,name,status,description,epicId";
    }
}

package service;

import model.Epic;
import model.Status;
import model.SubTask;
import model.Task;
import java.time.Duration;
import java.time.LocalDateTime;

public class CSVFormatter {

    private CSVFormatter() {

    }

    public static String toString(Task task) {
        return new StringBuilder()
                .append(task.getId()).append(",")
                .append(task.getName()).append(",")
                .append(task.getStatus()).append(",")
                .append(task.getDescription()).append(",")
                .append(task.getDuration().toMinutes()).append(",")
                .append(task.getStartTime())
                .toString();
    }

    public static String toString(SubTask task) {
        return new StringBuilder()
                .append(task.getId()).append(",")
                .append(task.getName()).append(",")
                .append(task.getStatus()).append(",")
                .append(task.getDescription()).append(",")
                .append(task.getEpicId()).append(",")
                .append(task.getDuration().toMinutes()).append(",")
                .append(task.getStartTime())
                .toString();
    }

    public static String toString(Epic task) {
        return new StringBuilder()
                .append(task.getId()).append(",")
                .append(task.getName()).append(",")
                .append(task.getStatus()).append(",")
                .append(task.getDescription()).append(",")
                .append(task.getDuration().toMinutes()).append(",")
                .append(task.getStartTime())
                .toString();
    }

    public static Task fromString(String value) {
        String[] parts = value.split(",");
        String id = parts[0];
        String name = parts[1];
        Status status = Status.valueOf(parts[2]);
        String description = parts[3];
        Duration duration = Duration.ofMinutes(Long.parseLong(parts[4]));
        LocalDateTime startTime = LocalDateTime.parse(parts[5]);
        return new Task(Integer.parseInt(id), name, status, description, duration, startTime);
    }

    public static SubTask fromSubTaskString(String value) {
        String[] parts = value.split(",");
        String id = parts[0];
        String name = parts[1];
        Status status = Status.valueOf(parts[2]);
        String description = parts[3];
        int epicId = Integer.parseInt(parts[4]);
        Duration duration = Duration.ofMinutes(Long.parseLong(parts[5]));
        LocalDateTime startTime = LocalDateTime.parse(parts[6]);
        return new SubTask(Integer.parseInt(id), name, status, description, epicId, duration, startTime);
    }

    public static String getHeader() {
        return "id,name,status,description,duration,startTime";
    }
}
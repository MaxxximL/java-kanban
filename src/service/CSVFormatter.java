package service;

import model.Status;
import model.Task;
import model.Epic;
import model.SubTask;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class CSVFormatter {

    public static String toCSV(Task task) {
        StringBuilder sb = new StringBuilder();
        sb.append(task.getId()).append(",");
        sb.append(task.getName()).append(",");
        sb.append(task.getDescription()).append(",");
        sb.append(task.getStatus());
        return sb.toString();
    }

    public static String toCSV(Epic epic) {
        StringBuilder sb = new StringBuilder();
        sb.append(epic.getId()).append(",");
        sb.append(epic.getName()).append(",");
        sb.append(epic.getDescription());
        return sb.toString();
    }

    public static String toCSV(SubTask subTask) {
        StringBuilder sb = new StringBuilder();
        sb.append(subTask.getId()).append(",");
        sb.append(subTask.getName()).append(",");
        sb.append(subTask.getDescription()).append(",");
        sb.append(subTask.getEpicId());
        return sb.toString();
    }

    public static Task fromString(String line) throws IOException {
        String[] fields = line.split(",");
        int id = Integer.parseInt(fields[0]);
        String name = fields[1];
        String description = fields[2];
        Status status;

        if (fields.length == 3) {
            status = Status.NEW;
        } else if (fields.length == 4) {
            status = Status.valueOf(fields[3]);
        } else {
            throw new IOException("Invalid CSV format");
        }

        if (description.isEmpty()) {
            return new Task(name, status);
        } else if (name.isEmpty()) {
            return new Task(id, description, status);
        } else if (status == Status.NEW) {
            return new Task(id, name, description, status);
        } else if (status == Status.DONE) {
            return new Epic(id, name, description);
        } else {
            return new SubTask(id, name, description, Status.NEW);
        }
    }

    public static List<Task> fromCSV(String csv) throws IOException {
        List<Task> tasks = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new StringReader(csv))) {
            String line;
            while ((line = br.readLine()) != null) {
                tasks.add(fromString(line));
            }
        }
        return tasks;
    }

    public static String getHeader() {

        return "id,name,description,status,epicId";
    }
}

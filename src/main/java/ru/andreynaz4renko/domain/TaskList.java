package ru.andreynaz4renko.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 *
 */
public class TaskList {
    private final List<Task> tasks;

    public TaskList() {
        tasks = new ArrayList<>();
    }

    public TaskList(List<Task> tasks) {
        this.tasks = tasks;
    }

    public boolean addTask(Task task) {
        return tasks.add(task);
    }

    public boolean removeTask(int id) {
        return tasks.removeIf(task -> task.getId() == id);
    }

    public boolean completeTask(int id) {
        Optional<Task> task = tasks.stream()
                .filter(t -> t.getId() == id)
                .findFirst();
        task.ifPresent(t -> {
            t.setStatus(TaskStatus.DONE);
            t.setCompletion(LocalDate.now());
        });
        return task.isPresent();
    }
}

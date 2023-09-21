package ru.andreynaz4renko.domain;

import java.time.LocalDate;

/**
 * // TODO
 */
public class Task {

    private final int id;
    private String caption;
    private String description;
    private int priority;
    private LocalDate deadline;
    private LocalDate completion;
    private TaskStatus status;


    public Task(int id,
                String caption,
                String description,
                int priority,
                LocalDate deadline,
                LocalDate completion,
                TaskStatus status) {
        if (caption == null || caption.length() > 50) {
            throw new IllegalArgumentException("Заголовок должен быть не длиннее 50 символов!");
        }
        if (priority < 0 || priority > 10) {
            throw new IllegalArgumentException("Приоритет должен быть в диапазоне от 0 до 10 включительно");
        }
        this.id = id;
        this.caption = caption;
        this.description = description;
        this.priority = priority;
        this.deadline = deadline;
        this.completion = completion;
        this.status = status;
    }

    public Task(int id,
                String caption,
                String description,
                int priority,
                LocalDate deadline) {
        this(id, caption, description, priority, deadline, null, TaskStatus.NEW);
    }

    public int getId() {
        return id;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public LocalDate getCompletion() {
        return completion;
    }

    public void setCompletion(LocalDate completion) {
        this.completion = completion;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    @Override
    public String toString() {
        return "Task ID: " + id +
                "\nCaption: " + caption +
                "\nDescription: " + description +
                "\nPriority: " + priority +
                "\nDeadline: " + deadline +
                "\nCompletion Date: " + completion +
                "\nStatus: " + status;
    }

}

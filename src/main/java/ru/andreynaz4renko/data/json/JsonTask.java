package ru.andreynaz4renko.data.json;

import jakarta.xml.bind.annotation.*;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import ru.andreynaz4renko.converters.LocalDateAdapter;
import ru.andreynaz4renko.data.xml.XmlTaskStatus;

import java.time.LocalDate;

public class JsonTask {
    @XmlAttribute
    private int id;
    @XmlAttribute
    private String caption;
    @XmlElement(name = "Description")
    private String description;
    @XmlElement(name = "Priority")
    private int priority;
    @XmlElement(name = "Deadline")
    @XmlJavaTypeAdapter(value = LocalDateAdapter.class)
    private LocalDate deadline;
    @XmlElement(name = "Complete")
    @XmlJavaTypeAdapter(value = LocalDateAdapter.class)
    private LocalDate completion;
    @XmlElement(name = "Status")
    private JsonTaskStatus status;

    public JsonTask() { }

    public JsonTask(int id,
                   String caption,
                   String description,
                   int priority,
                   LocalDate deadline,
                   LocalDate completion,
                   JsonTaskStatus status) {
        this.id = id;
        this.caption = caption;
        this.description = description;
        this.priority = priority;
        this.deadline = deadline;
        this.completion = completion;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public LocalDate getCompletion() {
        return completion;
    }

    public void setCompletion(LocalDate completion) {
        this.completion = completion;
    }

    public JsonTaskStatus getStatus() {
        return status;
    }

    public void setStatus(JsonTaskStatus status) {
        this.status = status;
    }
}


package ru.andreynaz4renko.data.xml;

import jakarta.xml.bind.annotation.*;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import ru.andreynaz4renko.converters.LocalDateAdapter;

import java.time.LocalDate;

@XmlRootElement(name = "Task")
@XmlAccessorType(XmlAccessType.FIELD)
public class XmlTask {
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
    private XmlTaskStatus status;

    public XmlTask() { }

    public XmlTask(int id,
                   String caption,
                   String description,
                   int priority,
                   LocalDate deadline,
                   LocalDate completion,
                   XmlTaskStatus status) {
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

    public XmlTaskStatus getStatus() {
        return status;
    }

    public void setStatus(XmlTaskStatus status) {
        this.status = status;
    }
}

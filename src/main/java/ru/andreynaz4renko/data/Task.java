package ru.andreynaz4renko.data;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.xml.bind.annotation.*;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import ru.andreynaz4renko.converters.LocalDateAdapter;
import ru.andreynaz4renko.converters.LocalDateDeserializer;
import ru.andreynaz4renko.converters.LocalDateSerializer;
import ru.andreynaz4renko.data.exceptions.InvalidCaptionException;
import ru.andreynaz4renko.data.exceptions.InvalidPriorityException;

import java.time.LocalDate;

/**
 * Класс {@link Task} представляет собой модель задачи с различными атрибутами, такими как идентификатор, заголовок,
 * описание, приоритет, срок выполнения и статус. Этот класс также используется в контексте JAXB и Jackson для
 * сериализации и десериализации задачи в и из форматов XML и JSON.
 */
@XmlRootElement(name = "Task")
@XmlAccessorType(XmlAccessType.FIELD)
public class Task {

    /**
     * Идентификатор задачи.
     */
    @XmlAttribute
    private int id;

    /**
     * Заголовок задачи.
     */
    @XmlAttribute
    private String caption;

    /**
     * Описание задачи.
     */
    @XmlElement(name = "Description")
    private String description;

    /**
     * Приоритет задачи (от 0 до 10).
     */
    @XmlElement(name = "Priority")
    private int priority;

    /**
     * Срок выполнения задачи.
     */
    @XmlElement(name = "Deadline")
    @XmlJavaTypeAdapter(value = LocalDateAdapter.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate deadline;

    /**
     * Дата завершения задачи.
     */
    @XmlElement(name = "Complete")
    @XmlJavaTypeAdapter(value = LocalDateAdapter.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate completion;

    /**
     * Статус задачи из перечисления {@link TaskStatus}.
     *
     * @see TaskStatus
     */
    @XmlElement(name = "Status")
    private TaskStatus status;

    /**
     * Конструктор по умолчанию. Создает пустой объект задачи. Необходим для десериализации.
     */
    @SuppressWarnings("unused")
    public Task() {
    }

    /**
     * Конструктор класса {@link Task}.
     *
     * @param id          Идентификатор задачи.
     * @param caption     Заголовок задачи (не более 50 символов).
     * @param description Описание задачи.
     * @param priority    Приоритет задачи [0; 10].
     * @param deadline    Дедлайн задачи.
     * @param completion  Дата завершения задачи.
     * @param status      Статус задачи.
     * @throws IllegalArgumentException Если {@link Task#caption} или {@link Task#priority} не удовлетворяют условиям.
     * @see TaskStatus
     * @see IllegalArgumentException
     */
    public Task(int id,
                String caption,
                String description,
                int priority,
                LocalDate deadline,
                LocalDate completion,
                TaskStatus status) throws InvalidCaptionException, InvalidPriorityException {
        validateCaption(caption);
        validatePriority(priority);
        this.id = id;
        this.caption = caption;
        this.description = description;
        this.priority = priority;
        this.deadline = deadline;
        this.completion = completion;
        this.status = status;
    }

    /**
     * Конструктор класса {@link Task}.
     * Используется для создания новой задачи в списке.
     * Поле {@link Task#completion} инициализируется null.
     * Поле {@link Task#status} инициализируется {@link TaskStatus#NEW}.
     *
     * @param id          Идентификатор задачи.
     * @param caption     Заголовок задачи (не более 50 символов).
     * @param description Описание задачи.
     * @param priority    Приоритет задачи [0; 10].
     * @param deadline    Дедлайн задачи.
     * @throws InvalidCaptionException  Если {@link Task#caption} не удовлетворяет условию.
     * @throws InvalidPriorityException Если {@link Task#priority} не удовлетворяет условию.
     * @see TaskStatus
     * @see IllegalArgumentException
     */
    public Task(int id,
                String caption,
                String description,
                int priority,
                LocalDate deadline) throws InvalidCaptionException, InvalidPriorityException {
        this(id, caption, description, priority, deadline, null, TaskStatus.NEW);
    }

    public int getId() {
        return id;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) throws InvalidCaptionException {
        validateCaption(caption);
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

    public void setPriority(int priority) throws InvalidPriorityException {
        validatePriority(priority);
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

    private static void validatePriority(int priority) throws InvalidPriorityException {
        if (priority < 0 || priority > 10) {
            throw new InvalidPriorityException("Priority must be in the range from 0 to 10 inclusive!");
        }
    }

    private static void validateCaption(String caption) throws InvalidCaptionException {
        if (caption == null || caption.length() > 50) {
            throw new InvalidCaptionException("Caption must be no longer than 50 characters!");
        }
    }

}

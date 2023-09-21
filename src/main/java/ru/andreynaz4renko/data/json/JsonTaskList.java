package ru.andreynaz4renko.data.json;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import ru.andreynaz4renko.data.xml.XmlTask;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс JsonTaskList представляет собой список задач, который может быть сериализован в JSON формат.
 * Он используется для преобразования списка задач в формат JSON и обратно.
 */
@XmlRootElement(name = "ToDoList")
@XmlAccessorType(XmlAccessType.FIELD)
public class JsonTaskList {

    /**
     * Список задач, представленных в формате JSON.
     */
    private List<JsonTask> tasks;

    /**
     * Конструктор по умолчанию. Создает новый пустой объект JsonTaskList.
     */
    public JsonTaskList() {
        tasks = new ArrayList<>();
    }

    /**
     * Конструктор, принимающий список задач для инициализации.
     *
     * @param tasks Список задач.
     */
    public JsonTaskList(List<JsonTask> tasks) {
        this.tasks = tasks;
    }

    /**
     * Возвращает список задач.
     *
     * @return Список задач.
     */
    public List<JsonTask> getTasks() {
        return tasks;
    }

    /**
     * Устанавливает список задач.
     *
     * @param tasks Список задач.
     */
    public void setTasks(List<JsonTask> tasks) {
        this.tasks = tasks;
    }
}
package ru.andreynaz4renko.data.xml;

import jakarta.xml.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс XmlTaskList представляет собой список задач, который может быть сериализован в XML формат.
 * Он используется для преобразования списка задач в формат XML и обратно.
 */
@XmlRootElement(name = "ToDoList")
@XmlAccessorType(XmlAccessType.FIELD)
public class XmlTaskList {

    /**
     * Список задач, представленных в формате XML.
     */
    @XmlElement(name = "Task")
    private List<XmlTask> tasks;

    /**
     * Конструктор по умолчанию. Создает новый пустой объект XmlTaskList.
     */
    public XmlTaskList() {
        tasks = new ArrayList<>();
    }

    /**
     * Конструктор, принимающий список задач для инициализации.
     *
     * @param tasks Список задач.
     */
    public XmlTaskList(List<XmlTask> tasks) {
        this.tasks = tasks;
    }

    /**
     * Возвращает список задач.
     *
     * @return Список задач.
     */
    public List<XmlTask> getTasks() {
        return tasks;
    }

    /**
     * Устанавливает список задач.
     *
     * @param tasks Список задач.
     */
    public void setTasks(List<XmlTask> tasks) {
        this.tasks = tasks;
    }
}

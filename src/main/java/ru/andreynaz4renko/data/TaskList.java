package ru.andreynaz4renko.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.istack.NotNull;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс {@link TaskList} представляет собой список задач, хранящий набор объектов типа {@link Task}.
 * Этот класс предоставляет методы для добавления, удаления и выполнения задач.
 *
 * @see Task
 */
@XmlRootElement(name = "ToDoList")
@XmlAccessorType(XmlAccessType.FIELD)
public class TaskList {

    /**
     * Список задач, представленных в виде коллекции объектов {@link Task}.
     *
     * @see Task
     */
    @XmlElement(name = "Task")
    @JsonProperty("ToDoList")
    private final List<Task> tasks;

    /**
     * Конструктор по умолчанию. Создает пустой список задач.
     */
    public TaskList() {
        tasks = new ArrayList<>();
    }

    /**
     * Конструктор, принимающий список задач для инициализации.
     *
     * @param tasks Список задач.
     * @see Task
     */
    public TaskList(@NotNull List<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * Добавляет задачу в список.
     *
     * @param task Задача для добавления.
     * @return true, если задача успешно добавлена в список, в противном случае - false.
     * @see Task
     */
    public boolean addTask(@NotNull Task task) {
        if (tasks.stream().noneMatch(t -> t.getId() == task.getId())) {
            return tasks.add(task);
        }
        return false;
    }

    /**
     * Удаляет задачу из списка по ее идентификатору.
     *
     * @param id Идентификатор задачи для удаления.
     * @return true, если задача с указанным идентификатором была успешно удалена, в противном случае - false.
     */
    public boolean removeTask(int id) {
        return tasks.removeIf(task -> task.getId() == id);
    }

    /**
     * Помечает задачу с указанным идентификатором как выполненную и устанавливает дату завершения.
     *
     * @param id Идентификатор задачи для пометки как выполненной.
     * @return true, если задача с указанным идентификатором была успешно помечена как выполненная,
     * в противном случае - false.
     */
    public boolean completeTask(int id) {
        return tasks.stream()
                .filter(task -> task.getId() == id)
                .findFirst()
                .map(task -> {
                    task.setStatus(TaskStatus.DONE);
                    task.setCompletion(LocalDate.now());
                    return true;
                })
                .orElse(false);
    }

    /**
     * Возвращает список задач.
     *
     * @return Список задач.
     * @see Task
     */
    public List<Task> getTasks() {
        return tasks;
    }
}

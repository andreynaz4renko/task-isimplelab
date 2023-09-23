package ru.andreynaz4renko.data;


import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс {@link TaskList} представляет собой список задач, хранящий набор объектов типа {@link Task}.
 * Этот класс предоставляет методы для добавления, удаления и выполнения задач.
 *
 * @see Task
 */
public class TaskList {

    /**
     * Список задач, представленных в виде коллекции объектов {@link Task}.
     *
     * @see Task
     */
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
     * @return {@code true}, если задача успешно добавлена в список, в противном случае - {@code false}.
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
     * @return {@code true}, если задача с указанным идентификатором была успешно удалена,
     * в противном случае - {@code false}.
     */
    public boolean removeTask(int id) {
        return tasks.removeIf(task -> task.getId() == id);
    }

    /**
     * Помечает задачу с указанным идентификатором как выполненную и устанавливает дату завершения.
     *
     * @param id Идентификатор задачи для пометки как выполненной.
     * @return {@code true}, если задача с указанным идентификатором была успешно помечена как выполненная,
     * в противном случае - {@code false}.
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

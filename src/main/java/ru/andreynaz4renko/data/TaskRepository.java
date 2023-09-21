package ru.andreynaz4renko.data;

import ru.andreynaz4renko.domain.Task;
import ru.andreynaz4renko.domain.TaskList;

import java.time.LocalDate;
import java.util.Comparator;

/**
 * Абстрактный класс TaskRepository представляет собой абстрактный репозиторий задач.
 * Репозиторий используется для управления списком задач, предоставляя методы для загрузки,
 * сохранения, добавления, редактирования и удаления задач.
 */
public abstract class TaskRepository {

    /**
     * Список задач, хранящийся в данном репозитории.
     */
    protected TaskList tasks;

    /**
     * Конструктор по умолчанию. Создает новый экземпляр TaskList и присваивает его полю tasks.
     */
    public TaskRepository() {
        tasks = new TaskList();
    }

    /**
     * Абстрактный метод для загрузки задач из внешнего источника данных.
     *
     * @return true, если задачи успешно загружены, в противном случае - false.
     */
    public abstract boolean loadTasks();

    /**
     * Абстрактный метод для сохранения задач во внешний источник данных.
     *
     * @return true, если задачи успешно сохранены, в противном случае - false.
     */
    public abstract boolean saveTasks();

    /**
     * Проверяет, существует ли задача с указанным идентификатором.
     *
     * @param id Идентификатор задачи для проверки.
     * @return true, если задача с указанным идентификатором существует, в противном случае - false.
     */
    public boolean isTaskExists(int id) {
        return tasks.getTasks().stream()
                .anyMatch(task -> task.getId() == id);
    }

    /**
     * Возвращает максимальный идентификатор задачи в списке задач.
     *
     * @return Максимальный идентификатор задачи или 0, если список задач пуст.
     */
    public int getMaxTaskId() {
        return tasks.getTasks().stream()
                .max(Comparator.comparing(Task::getId))
                .map(Task::getId)
                .orElse(0);
    }

    /**
     * Добавляет задачу в список задач.
     *
     * @param task Задача для добавления.
     * @return true, если задача успешно добавлена, в противном случае - false.
     */
    public boolean addTask(Task task) {
        return tasks.addTask(task);
    }

    /**
     * Редактирует задачу с указанным идентификатором, обновляя указанные атрибуты задачи.
     *
     * @param id          Идентификатор задачи для редактирования.
     * @param caption     Новый заголовок задачи.
     * @param description Новое описание задачи.
     * @param priority    Новый приоритет задачи.
     * @param deadline    Новый срок выполнения задачи.
     * @return true, если задача успешно отредактирована, в противном случае - false.
     */
    public boolean editTask(int id,
                            String caption,
                            String description,
                            String priority,
                            String deadline) {
        return tasks.getTasks().stream()
                .filter(task -> task.getId() == id)
                .findFirst()
                .map(task -> {
                    if (caption != null && !caption.isEmpty()) {
                        task.setCaption(caption);
                    }
                    if (caption != null && !description.isEmpty()) {
                        task.setDescription(description);
                    }
                    if (!priority.isEmpty()) {
                        task.setPriority(Integer.parseInt(priority));
                    }
                    if (deadline != null && !deadline.isEmpty()) {
                        task.setDeadline(LocalDate.parse(deadline));
                    }
                    return true;
                })
                .orElse(false);
    }

    /**
     * Удаляет задачу с указанным идентификатором из списка задач.
     *
     * @param id Идентификатор задачи для удаления.
     * @return true, если задача успешно удалена, в противном случае - false.
     */
    public boolean removeTask(int id) {
        return tasks.removeTask(id);
    }

    /**
     * Помечает задачу с указанным идентификатором как выполненную.
     *
     * @param id Идентификатор задачи для пометки как выполненной.
     * @return true, если задача успешно помечена как выполненная, в противном случае - false.
     */
    public boolean completeTask(int id) {
        return tasks.completeTask(id);
    }

    /**
     * Возвращает список задач, хранящийся в данном репозитории.
     *
     * @return Список задач.
     */
    public TaskList getTasksList() {
        return tasks;
    }

}

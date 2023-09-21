package ru.andreynaz4renko.converters;

import ru.andreynaz4renko.data.json.JsonTask;
import ru.andreynaz4renko.data.json.JsonTaskList;
import ru.andreynaz4renko.data.json.JsonTaskStatus;
import ru.andreynaz4renko.data.xml.XmlTask;
import ru.andreynaz4renko.data.xml.XmlTaskList;
import ru.andreynaz4renko.data.xml.XmlTaskStatus;
import ru.andreynaz4renko.domain.Task;
import ru.andreynaz4renko.domain.TaskList;
import ru.andreynaz4renko.domain.TaskStatus;

public class TaskConverter {

    /**
     * Преобразует статус задачи типа TaskStatus в XML-представление XmlTaskStatus.
     *
     * @param status Статус задачи типа TaskStatus.
     * @return XML-представление статуса задачи XmlTaskStatus.
     */
    public static XmlTaskStatus taskStatusToXmlTaskStatus(TaskStatus status) {
        switch (status) {
            case NEW -> {
                return XmlTaskStatus.NEW;
            }
            case IN_PROGRESS -> {
                return XmlTaskStatus.IN_PROGRESS;
            }
            case DONE -> {
                return XmlTaskStatus.DONE;
            }
        }
        return XmlTaskStatus.NEW;
    }

    /**
     * Преобразует XML-представление XmlTaskStatus статуса задачи в тип TaskStatus.
     *
     * @param status XML-представление статуса задачи XmlTaskStatus.
     * @return Статус задачи типа TaskStatus.
     */
    public static TaskStatus xmlTaskStatusToTaskStatus(XmlTaskStatus status) {
        switch (status) {
            case NEW -> {
                return TaskStatus.NEW;
            }
            case IN_PROGRESS -> {
                return TaskStatus.IN_PROGRESS;
            }
            case DONE -> {
                return TaskStatus.DONE;
            }
        }
        return TaskStatus.NEW;
    }

    /**
     * Преобразует статус задачи типа TaskStatus в JSON-представление JsonTaskStatus.
     *
     * @param status Статус задачи типа TaskStatus.
     * @return JSON-представление статуса задачи JsonTaskStatus.
     */
    public static JsonTaskStatus taskStatusToJsonTaskStatus(TaskStatus status) {
        switch (status) {
            case NEW -> {
                return JsonTaskStatus.NEW;
            }
            case IN_PROGRESS -> {
                return JsonTaskStatus.IN_PROGRESS;
            }
            case DONE -> {
                return JsonTaskStatus.DONE;
            }
        }
        return JsonTaskStatus.NEW;
    }

    /**
     * Преобразует JSON-представление JsonTaskStatus статуса задачи в тип TaskStatus.
     *
     * @param status JSON-представление статуса задачи JsonTaskStatus.
     * @return Статус задачи типа TaskStatus.
     */
    public static TaskStatus jsonTaskStatusToTaskStatus(JsonTaskStatus status) {
        switch (status) {
            case NEW -> {
                return TaskStatus.NEW;
            }
            case IN_PROGRESS -> {
                return TaskStatus.IN_PROGRESS;
            }
            case DONE -> {
                return TaskStatus.DONE;
            }
        }
        return TaskStatus.NEW;
    }

    /**
     * Преобразует объект задачи типа Task в его XML-представление XmlTask.
     *
     * @param task Объект задачи типа Task.
     * @return XML-представление задачи типа XmlTask.
     */
    public static XmlTask taskToXmlTask(Task task) {
        return new XmlTask(task.getId(),
                task.getCaption(),
                task.getDescription(),
                task.getPriority(),
                task.getDeadline(),
                task.getCompletion(),
                taskStatusToXmlTaskStatus(task.getStatus()));
    }

    /**
     * Преобразует XML-представление задачи типа XmlTask в объект задачи типа Task.
     *
     * @param task XML-представление задачи типа XmlTask.
     * @return Объект задачи типа Task.
     */
    public static Task xmlTaskToTask(XmlTask task) {
        return new Task(task.getId(),
                task.getCaption(),
                task.getDescription(),
                task.getPriority(),
                task.getDeadline(),
                task.getCompletion(),
                xmlTaskStatusToTaskStatus(task.getStatus()));
    }

    /**
     * Преобразует объект задачи типа Task в его JSON-представление JsonTask.
     *
     * @param task Объект задачи типа Task.
     * @return JSON-представление задачи типа JsonTask.
     */
    public static JsonTask taskToJsonTask(Task task) {
        return new JsonTask(task.getId(),
                task.getCaption(),
                task.getDescription(),
                task.getPriority(),
                task.getDeadline(),
                task.getCompletion(),
                taskStatusToJsonTaskStatus(task.getStatus()));
    }

    /**
     * Преобразует JSON-представление задачи типа JsonTask в объект задачи типа Task.
     *
     * @param task JSON-представление задачи типа JsonTask.
     * @return Объект задачи типа Task.
     */
    public static Task jsonTaskToTask(JsonTask task) {
        return new Task(task.getId(),
                task.getCaption(),
                task.getDescription(),
                task.getPriority(),
                task.getDeadline(),
                task.getCompletion(),
                jsonTaskStatusToTaskStatus(task.getStatus()));
    }

    /**
     * Преобразует список задач типа TaskList в его XML-представление типа XmlTaskList.
     *
     * @param taskList Список задач типа TaskList.
     * @return XML-представление списка задач типа XmlTaskList.
     */
    public static XmlTaskList taskListToXmlTaskList(TaskList taskList) {
        return new XmlTaskList(taskList.getTasks().stream()
                .map(TaskConverter::taskToXmlTask)
                .toList());
    }

    /**
     * Преобразует XML-представление списка задач типа XmlTaskList в объект типа TaskList.
     *
     * @param xmlTaskList XML-представление списка задач типа XmlTaskList.
     * @return Объект списка задач типа TaskList.
     */
    public static TaskList xmlTaskListToTaskList(XmlTaskList xmlTaskList) {
        return new TaskList(xmlTaskList.getTasks().stream()
                .map(TaskConverter::xmlTaskToTask)
                .toList());
    }

    /**
     * Преобразует список задач типа TaskList в его JSON-представление типа JsonTaskList.
     *
     * @param taskList Список задач типа TaskList.
     * @return JSON-представление списка задач типа JsonTaskList.
     */
    public static JsonTaskList taskListToJsonTaskList(TaskList taskList) {
        return new JsonTaskList(taskList.getTasks().stream()
                .map(TaskConverter::taskToJsonTask)
                .toList());
    }

    /**
     * Преобразует JSON-представление списка задач типа JsonTaskList в объект типа TaskList.
     *
     * @param jsonTaskList JSON-представление списка задач типа JsonTaskList.
     * @return Объект списка задач типа TaskList.
     */
    public static TaskList jsonTaskListToTaskList(JsonTaskList jsonTaskList) {
        return new TaskList(jsonTaskList.getTasks().stream()
                .map(TaskConverter::jsonTaskToTask)
                .toList());
    }

}

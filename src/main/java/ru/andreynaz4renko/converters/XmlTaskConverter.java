package ru.andreynaz4renko.converters;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import ru.andreynaz4renko.data.Task;
import ru.andreynaz4renko.data.TaskList;
import ru.andreynaz4renko.data.TaskStatus;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Класс {@link XmlTaskConverter} предоставляет статические методы для конвертации между
 * объектами задач типа {@link Task} и их представлениями в формате XML ({@link Document}).
 * Этот класс используется для преобразования данных о задачах в XML и обратно.
 *
 * @see Task
 * @see Document
 */
public class XmlTaskConverter {

    public static final String TODO_ELEMENT = "ToDoList";
    public static final String TASK_ELEMENT = "Task";
    public static final String ID_TASK_ARGUMENT = "id";
    public static final String CAPTION_TASK_ARGUMENT = "caption";
    public static final String DESCRIPTION_TASK_ELEMENT = "Description";
    public static final String PRIORITY_TASK_ELEMENT = "Priority";
    public static final String DEADLINE_TASK_ELEMENT = "Deadline";
    public static final String COMPLETE_TASK_ELEMENT = "Complete";
    public static final String STATUS_TASK_ELEMENT = "Status";

    /**
     * Получает список задач из объекта {@link Document}.
     * <p>
     * Пример XML-документа:
     * <pre>{@code
     * <?xml version="1.0" encoding="UTF-8" standalone="yes"?>
     * <ToDoList>
     *     <Task id="1" caption="Заголовок задачи">
     *         <Description>Описание задачи</Description>
     *         <Priority>10</Priority>
     *         <Deadline>2017-02-12</Deadline>
     *         <Complete>2017-02-19</Complete>
     *         <Status>done</Status>
     *     </Task>
     *     <Task id="2" caption="Заголовок еще одной задачи">
     *         <Description>Описание еще одной задачи</Description>
     *         <Priority>10</Priority>
     *         <Deadline>2022-02-12</Deadline>
     *         <Status>new</Status>
     *     </Task>
     * </ToDoList>
     * }</pre>
     *
     * @param document Объект {@link Document}, содержащий задачи в формате XML.
     * @return Объект {@link TaskList}, содержащий задачи из объекта {@link Document}.
     * @see Document
     * @see TaskList
     */
    public static TaskList getTaskListFromDocument(Document document) {
        NodeList nodes = document.getElementsByTagName(TASK_ELEMENT);
        List<Element> tasks = new ArrayList<>();
        for (int i = 0; i < nodes.getLength(); i++) {
            tasks.add((Element) nodes.item(i));
        }

        return new TaskList(tasks.stream()
                .map(XmlTaskConverter::nodeToTask)
                .filter(Objects::nonNull)
                .collect(Collectors.toList()));
    }

    /**
     * Преобразует список задач типа {@link TaskList} в объект типа {@link Node} для включения в XML-документ.
     *
     * @param taskList Список задач типа {@link TaskList}.
     * @param document Объект {@link Document}, в который будут добавлены задачи.
     * @return Объект {@link Node}, представляющий список задач в формате XML.
     * @see TaskList
     * @see Node
     * @see Document
     */
    public static Node tasksToTaskNodeList(TaskList taskList, Document document) {
        Node tasksNodeList = document.createElement(TODO_ELEMENT);
        taskList.getTasks().forEach(task -> tasksNodeList.appendChild(taskToNode(task, document)));
        return tasksNodeList;
    }

    /**
     * Преобразует задачу типа {@link Task} в объект типа {@link Node} для включения в XML-документ.
     *
     * @param task     Задача типа {@link Task}.
     * @param document Объект {@link Document}, в который будет добавлена задача.
     * @return Объект {@link Node}, представляющий задачу в формате XML.
     * @see Task
     * @see Node
     * @see Document
     */
    public static Node taskToNode(Task task, Document document) {
        Element taskElement = document.createElement(TASK_ELEMENT);
        taskElement.setAttribute(ID_TASK_ARGUMENT, String.valueOf(task.getId()));

        if (task.getCaption() != null) {
            taskElement.setAttribute(CAPTION_TASK_ARGUMENT, task.getCaption());
        }

        if (task.getDescription() != null) {
            Element descriptionElement = document.createElement(DESCRIPTION_TASK_ELEMENT);
            descriptionElement.setTextContent(task.getDescription());
            taskElement.appendChild(descriptionElement);
        }

        Element priorityElement = document.createElement(PRIORITY_TASK_ELEMENT);
        priorityElement.setTextContent(String.valueOf(task.getPriority()));
        taskElement.appendChild(priorityElement);

        if (task.getDeadline() != null) {
            Element deadlineElement = document.createElement(DEADLINE_TASK_ELEMENT);
            deadlineElement.setTextContent(task.getDeadline().toString());
            taskElement.appendChild(deadlineElement);
        }

        if (task.getCompletion() != null) {
            Element completeElement = document.createElement(COMPLETE_TASK_ELEMENT);
            completeElement.setTextContent(task.getCompletion().toString());
            taskElement.appendChild(completeElement);
        }

        if (task.getStatus() != null) {
            Element statusElement = document.createElement(STATUS_TASK_ELEMENT);
            statusElement.setTextContent(task.getStatus().name());
            taskElement.appendChild(statusElement);
        }

        return taskElement;
    }

    /**
     * Преобразует объект типа {@link Node}, представляющий задачу в формате XML, в объект задачи типа {@link Task}.
     *
     * @param task Объект {@link Node}, представляющий задачу в формате XML.
     * @return Объект задачи типа {@link Task}.
     * @see Task
     * @see Node
     */
    public static Task nodeToTask(Element task) {
        try {
            int id = Integer.parseInt(task.getAttribute(ID_TASK_ARGUMENT));
            String caption = task.getAttribute(CAPTION_TASK_ARGUMENT);
            Node descriptionElement = task.getElementsByTagName(DESCRIPTION_TASK_ELEMENT).item(0);
            Node priorityElement = task.getElementsByTagName(PRIORITY_TASK_ELEMENT).item(0);
            Node deadlineElement = task.getElementsByTagName(DEADLINE_TASK_ELEMENT).item(0);
            Node completeElement = task.getElementsByTagName(COMPLETE_TASK_ELEMENT).item(0);
            Node statusElement = task.getElementsByTagName(STATUS_TASK_ELEMENT).item(0);

            if (caption.isEmpty()) {
                throw new Exception();
            }

            String description = null;
            if (descriptionElement != null) {
                description = descriptionElement.getTextContent();
            }

            int priority = -1;
            if (priorityElement != null) {
                priority = Integer.parseInt(priorityElement.getTextContent());
            }

            LocalDate deadline = null;
            if (deadlineElement != null) {
                deadline = LocalDate.parse(deadlineElement.getTextContent());
            }

            LocalDate complete = null;
            if (completeElement != null) {
                complete = LocalDate.parse(completeElement.getTextContent());
            }

            TaskStatus status = TaskStatus.NEW;
            if (statusElement != null) {
                status = TaskStatus.valueOf(statusElement.getTextContent().toUpperCase());
            }

            return new Task(id, caption, description, priority, deadline, complete, status);
        } catch (Exception e) {
            return null;
        }
    }

}

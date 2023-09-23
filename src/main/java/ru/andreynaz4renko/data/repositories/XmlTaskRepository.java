package ru.andreynaz4renko.data.repositories;

import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.FileInputStream;
import java.io.FileWriter;

import static ru.andreynaz4renko.converters.XmlTaskConverter.getTaskListFromDocument;
import static ru.andreynaz4renko.converters.XmlTaskConverter.tasksToTaskNodeList;

/**
 * Класс XmlTaskRepository представляет собой репозиторий задач, способный сохранять и загружать задачи в формате XML.
 * Он расширяет абстрактный класс TaskRepository для маршализации и демаршализации задач в и из формата XML.
 *
 * @see TaskRepository
 */
public class XmlTaskRepository extends TaskRepository {

    /**
     * Путь к файлу, в котором хранятся задачи в формате XML.
     */
    private final String filepath;

    private final DocumentBuilder builder;


    /**
     * Конструктор класса {@link XmlTaskRepository}.
     * <p>
     * Пример XML-файла:
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
     * @param filepath Путь к файлу, в котором хранятся задачи в формате XML.
     */
    public XmlTaskRepository(@NotNull String filepath) throws ParserConfigurationException {
        this.filepath = filepath;

        this.builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
    }

    /**
     * Загружает задачи из файла в формате XML и преобразует их в список задач.
     *
     * @return {@code true}, если задачи успешно загружены, в противном случае - {@code false}.
     */
    @Override
    public boolean loadTasks() {
        try (FileInputStream reader = new FileInputStream(filepath)) {
            Document document = builder.parse(reader);
            tasks = getTaskListFromDocument(document);
            return true;
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
        return false;
    }

    /**
     * Сохраняет список задач в формате XML в указанный файл.
     *
     * @return {@code true}, если задачи успешно сохранены, в противном случае - {@code false}.
     */
    @Override
    public boolean saveTasks() {
        try {
            Document document = builder.newDocument();
            DOMSource domSource = new DOMSource(tasksToTaskNodeList(tasks, document));
            try (FileWriter fileWriter = new FileWriter(filepath)) {
                StreamResult streamResult = new StreamResult(fileWriter);

                Transformer transformer = TransformerFactory.newInstance().newTransformer();
                transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
                transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                transformer.transform(domSource, streamResult);
            }
            return true;
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
        return false;
    }
}

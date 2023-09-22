package ru.andreynaz4renko.data.repositories;

import com.sun.istack.NotNull;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import ru.andreynaz4renko.data.Task;
import ru.andreynaz4renko.data.TaskList;

import java.io.FileReader;
import java.io.FileWriter;

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

    /**
     * Объект Marshaller для маршализации задач в XML формат.
     */
    private final Marshaller marshaller;

    /**
     * Объект Unmarshaller для демаршализации задач из XML формата.
     */
    private final Unmarshaller unmarshaller;

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
     * @throws JAXBException В случае ошибки при инициализации {@link JAXBContext}.
     * @see JAXBContext
     * @see JAXBException
     */
    public XmlTaskRepository(@NotNull String filepath) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(TaskList.class, Task.class);
        this.marshaller = context.createMarshaller();
        this.marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        this.unmarshaller = context.createUnmarshaller();
        this.filepath = filepath;
    }

    /**
     * Загружает задачи из файла в формате XML и преобразует их в список задач.
     *
     * @return true, если задачи успешно загружены, в противном случае - false.
     */
    @Override
    public boolean loadTasks() {
        try (FileReader reader = new FileReader(filepath)) {
            tasks = (TaskList) unmarshaller.unmarshal(reader);
            return true;
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
        return false;
    }

    /**
     * Сохраняет список задач в формате XML в указанный файл.
     *
     * @return true, если задачи успешно сохранены, в противном случае - false.
     */
    @Override
    public boolean saveTasks() {
        try (FileWriter writer = new FileWriter(filepath)) {
            marshaller.marshal(tasks, writer);
            return true;
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
        return false;
    }
}

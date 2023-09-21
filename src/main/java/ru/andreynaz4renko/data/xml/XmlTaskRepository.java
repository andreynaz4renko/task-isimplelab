package ru.andreynaz4renko.data.xml;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import ru.andreynaz4renko.converters.TaskConverter;
import ru.andreynaz4renko.data.TaskRepository;

import java.io.FileReader;
import java.io.FileWriter;

/**
 * Класс XmlTaskRepository представляет собой репозиторий задач, способный сохранять и загружать задачи в формате XML.
 * Он расширяет абстрактный класс TaskRepository и использует JAXB для маршализации и демаршализации данных задачи
 * в и из формата XML.
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
     * Конструктор класса XmlTaskRepository.
     *
     * @param filepath Путь к файлу, в котором хранятся задачи в формате XML.
     * @throws JAXBException В случае ошибки при инициализации JAXBContext.
     */
    public XmlTaskRepository(String filepath) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(XmlTaskList.class, XmlTask.class);
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
            XmlTaskList xmlTaskList = (XmlTaskList) unmarshaller.unmarshal(reader);
            tasks = TaskConverter.xmlTaskListToTaskList(xmlTaskList);
            return true;
        } catch (Exception e) {
            System.err.println(e.getLocalizedMessage());
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
            XmlTaskList xmlTaskList = TaskConverter.taskListToXmlTaskList(tasks);
            marshaller.marshal(xmlTaskList, writer);
            return true;
        } catch (Exception e) {
            System.err.println(e.getLocalizedMessage());
        }
        return false;
    }
}

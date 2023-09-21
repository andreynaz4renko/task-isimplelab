package ru.andreynaz4renko.data.json;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import ru.andreynaz4renko.converters.TaskConverter;
import ru.andreynaz4renko.data.TaskRepository;
import ru.andreynaz4renko.data.xml.XmlTask;
import ru.andreynaz4renko.data.xml.XmlTaskList;

import java.io.FileReader;
import java.io.FileWriter;

public class JsonTaskRepository extends TaskRepository {

    private final String filepath;
    private final Marshaller marshaller;
    private final Unmarshaller unmarshaller;

    public JsonTaskRepository(String filepath) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(XmlTaskList.class, XmlTask.class);
        this.marshaller = context.createMarshaller();
        this.marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        this.unmarshaller = context.createUnmarshaller();
        this.filepath = filepath;
    }

    @Override
    public boolean loadTasks() {
        try (FileReader reader = new FileReader(filepath)) {
            JsonTaskList jsonTaskList = (JsonTaskList) unmarshaller.unmarshal(reader);
            tasks = TaskConverter.jsonTaskListToTaskList(jsonTaskList);
            return true;
        } catch (Exception e) {
            System.err.println(e.getLocalizedMessage());
        }
        return false;
    }

    @Override
    public boolean saveTasks() {
        try (FileWriter writer = new FileWriter(filepath)) {
            JsonTaskList jsonTaskList = TaskConverter.taskListToJsonTaskList(tasks);
            marshaller.marshal(jsonTaskList, writer);
            return true;
        } catch (Exception e) {
            System.err.println(e.getLocalizedMessage());
        }
        return false;
    }
}

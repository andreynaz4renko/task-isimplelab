package ru.andreynaz4renko;

import jakarta.xml.bind.JAXBException;
import ru.andreynaz4renko.data.TaskRepository;
import ru.andreynaz4renko.data.xml.XmlTaskRepository;

public class Main {
    public static void main(String[] args) throws JAXBException {
        TaskRepository repository = new XmlTaskRepository("tasks.xml");
        TaskManager taskManager = new TaskManager(repository);
        taskManager.run();
    }
}
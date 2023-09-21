package ru.andreynaz4renko;

import jakarta.xml.bind.JAXBException;
import ru.andreynaz4renko.data.repositories.TaskRepository;
import ru.andreynaz4renko.data.repositories.XmlTaskRepository;

import java.io.FileNotFoundException;
import java.net.URL;

public class Main {
    public static void main(String[] args) throws JAXBException, FileNotFoundException {
        URL xmlFileUrl = Main.class.getClassLoader().getResource("tasks.xml");
        // URL jsonFileUrl = Main.class.getClassLoader().getResource("tasks.json");

        TaskRepository repository = null;
        if (xmlFileUrl != null) {
            repository = new XmlTaskRepository(xmlFileUrl.getPath());
        }

        // if (jsonFileUrl != null) {
        //     repository = new JsonTaskRepository(jsonFileUrl.getPath());
        // }

        // save не работает
        // repository = new RestTaskRepository("https://mocki.io/v1/6aed3f0b-9334-44b9-87ed-489f5559b0b5");

        if (repository != null) {
            TaskManager taskManager = new TaskManager(repository);
            taskManager.run();
        } else {
            throw new FileNotFoundException("Resource file for repository not found!");
        }
    }
}
package ru.andreynaz4renko;

import ru.andreynaz4renko.data.repositories.TaskRepository;
import ru.andreynaz4renko.data.repositories.XmlTaskRepository;

import javax.xml.parsers.ParserConfigurationException;
import java.io.FileNotFoundException;
import java.net.URL;

public class Main {
    public static void main(String[] args) throws FileNotFoundException, ParserConfigurationException {
        URL xmlFileUrl = Main.class.getClassLoader().getResource("tasks.xml");

        TaskRepository repository = null;
        if (xmlFileUrl != null) {
            repository = new XmlTaskRepository(xmlFileUrl.getPath());
        }
        if (repository != null) {
            CommandManager manager = new CommandManager(repository);
            manager.run();
        } else {
            throw new FileNotFoundException("Resource file for repository not found!");
        }
    }
}
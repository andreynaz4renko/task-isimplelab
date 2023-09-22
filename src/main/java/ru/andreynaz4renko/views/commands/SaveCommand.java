package ru.andreynaz4renko.views.commands;

import ru.andreynaz4renko.data.repositories.TaskRepository;

import java.util.List;

public class SaveCommand extends RepositoryCommand {

    public SaveCommand(TaskRepository repository) {
        super(repository);
        help = "save               - Save tasks into repository";
    }

    @Override
    public boolean execute(List<String> args) {
        if (repository.saveTasks()) {
            System.out.println("Tasks successfully saved.");
            return true;
        }
        System.out.println("Fail to save tasks.");
        return false;
    }
}

package ru.andreynaz4renko.views.commands;

import ru.andreynaz4renko.data.repositories.TaskRepository;

import java.util.List;

public class LoadCommand extends RepositoryCommand {

    public LoadCommand(TaskRepository repository) {
        super(repository);
        help = "load               - Load tasks from repository";
    }

    @Override
    public boolean execute(List<String> args) {
        if (repository.loadTasks()) {
            System.out.println("Tasks successfully loaded.");
            return true;
        }
        System.out.println("Fail to load tasks.");
        return false;
    }
}

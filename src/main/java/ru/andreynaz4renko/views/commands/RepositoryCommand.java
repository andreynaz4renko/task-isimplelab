package ru.andreynaz4renko.views.commands;

import ru.andreynaz4renko.data.repositories.TaskRepository;

import java.util.List;

public abstract class RepositoryCommand {

    protected String help;
    protected TaskRepository repository;

    public RepositoryCommand(TaskRepository repository) throws NullPointerException {
        if (repository == null) {
            throw new NullPointerException("Repository can't be null!");
        }
        this.repository = repository;
    }

    public boolean execute() {
        return execute(null);
    }

    public abstract boolean execute(List<String> args);

    public String getHelp() {
        return help;
    }
}

package ru.andreynaz4renko.commands;

import ru.andreynaz4renko.data.repositories.TaskRepository;

import java.util.List;

/**
 * Класс LoadCommand представляет команду для загрузки задач из репозитория.
 * Эта команда позволяет загрузить задачи из хранилища в текущий репозиторий задач.
 */
public class LoadCommand extends RepositoryCommand {

    public LoadCommand(TaskRepository repository) {
        super(repository);
        help = "load               - Load tasks from repository";
    }

    /**
     * Выполняет команду загрузки задач из хранилища в текущий репозиторий.
     *
     * @param args Список аргументов команды (игнорируются).
     * @return {@code true}, если задачи успешно загружены, в противном случае - {@code false}.
     */
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

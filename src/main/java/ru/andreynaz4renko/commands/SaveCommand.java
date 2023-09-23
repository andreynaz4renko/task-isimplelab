package ru.andreynaz4renko.commands;

import ru.andreynaz4renko.data.repositories.TaskRepository;

import java.util.List;

/**
 * Класс SaveCommand представляет команду для сохранения задач в репозиторий.
 * Эта команда позволяет сохранить текущее состояние задач в хранилище.
 */
public class SaveCommand extends RepositoryCommand {

    public SaveCommand(TaskRepository repository) {
        super(repository);
        help = "save               - Save tasks into repository";
    }

    /**
     * Выполняет команду сохранения задач в хранилище.
     *
     * @param args Список аргументов команды (игнорируются).
     * @return {@code true}, если задачи успешно сохранены, в противном случае - {@code false}.
     */
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

package ru.andreynaz4renko.commands;

import ru.andreynaz4renko.data.repositories.TaskRepository;

import java.util.List;

/**
 * Класс RemoveCommand представляет команду для удаления задачи из репозитория.
 * Эта команда позволяет удалить задачу по её идентификатору.
 */
public class RemoveCommand extends RepositoryCommand {

    public RemoveCommand(TaskRepository repository) {
        super(repository);
        this.help = "remove [id]        - Remove a task";
        this.argsCountList = List.of(1);
    }

    /**
     * Выполняет команду удаления задачи на основе переданных аргументов.
     *
     * @param args Список аргументов команды. Должен содержать идентификатор задачи.
     * @return {@link true}, если задача успешно удалена, в противном случае - {@link false}.
     */
    @Override
    public boolean execute(List<String> args) {
        if (args == null) {
            System.out.println("The task cannot be removed. Make sure you have entered a task id.");
            return false;
        }
        try {
            if (args.size() == 1) {
                int id = Integer.parseInt(args.get(0));
                if (repository.removeTask(id)) {
                    System.out.println("Task removed.");
                    return true;
                }
                System.out.println("Fail to remove task.");
            }
        } catch (Exception e) {
            System.out.println("Make sure you have entered a number as a task id. " + e.getLocalizedMessage());
        }
        System.out.println("The task cannot be removed.");
        return false;
    }
}

package ru.andreynaz4renko.commands;

import ru.andreynaz4renko.data.TaskStatus;
import ru.andreynaz4renko.data.repositories.TaskRepository;

import java.util.List;

/**
 * Класс CompleteCommand представляет команду для отметки задачи как завершенной в репозитории задач.
 * Эта команда позволяет изменить статус задачи на {@link TaskStatus#DONE} по её идентификатору.
 *
 * @see TaskStatus
 */
public class CompleteCommand extends RepositoryCommand {

    public CompleteCommand(TaskRepository repository) {
        super(repository);
        help = "complete [id]      - Mark a task as completed";
        argsCountList = List.of(1);
    }

    /**
     * Выполняет команду отметки задачи как завершенной на основе переданных аргументов.
     *
     * @param args Список аргументов команды. Должен содержать идентификатор задачи.
     * @return {@code true}, если задача успешно отмечена как завершенная, в противном случае - {@code false}.
     */
    @Override
    public boolean execute(List<String> args) {
        if (args == null) {
            System.out.println("The task cannot be marked as completed. Make sure you have entered a task id.");
            return false;
        }
        if (args.size() == 1) {
            try {
                int id = Integer.parseInt(args.get(0));
                if (repository.completeTask(id)) {
                    System.out.println("Task marked as completed.");
                    return true;
                }
                System.out.println("Task not found or already completed.");
            } catch (Exception e) {
                System.out.println("The task cannot be marked as completed. Make sure you have entered an int task id.");
            }
        }
        return false;
    }
}

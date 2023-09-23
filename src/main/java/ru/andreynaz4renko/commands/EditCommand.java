package ru.andreynaz4renko.commands;

import ru.andreynaz4renko.data.repositories.TaskRepository;

import java.util.List;
import java.util.Scanner;

/**
 * Класс EditCommand представляет команду для редактирования существующей задачи в репозитории.
 * Эта команда позволяет изменить параметры задачи по её идентификатору.
 */
public class EditCommand extends RepositoryCommand {

    public EditCommand(TaskRepository repository, Scanner scanner) {
        super(repository);
        help = "edit [id]          - Edit an existing task";
        argsCountList = List.of(1);
        this.scanner = scanner;
    }

    /**
     * Выполняет команду редактирования задачи на основе переданных аргументов.
     *
     * @param args Список аргументов команды. Должен содержать идентификатор задачи.
     * @return {@code true}, если задача успешно отредактирована; в противном случае - {@code false}.
     */
    @Override
    public boolean execute(List<String> args) {
        if (args == null) {
            System.out.println("The task cannot be edited. Make sure you have entered a task id.");
            return false;
        }
        try {
            if (args.size() == 1) {
                int id = Integer.parseInt(args.get(0));
                if (repository.isTaskExists(id)) {
                    System.out.print("Enter new caption (leave empty to keep current): ");
                    String title = scanner.nextLine();
                    System.out.print("Enter new description (leave empty to keep current): ");
                    String description = scanner.nextLine();
                    System.out.print("Enter new priority (leave empty to keep current): ");
                    String priority = scanner.nextLine();
                    System.out.print("Enter new deadline (leave empty to keep current): ");
                    String deadline = scanner.nextLine();

                    if (repository.editTask(id, title, description, priority, deadline)) {
                        System.out.println("Task edited.");
                        return true;
                    }
                    System.out.println("Fail to edit task.");
                }
                System.out.println("Task not found.");
            }
        } catch (Exception e) {
            System.out.println("Make sure you have entered a number as a task id. " + e.getLocalizedMessage());
        }
        System.out.println("The task cannot be edited.");
        return false;
    }
}

package ru.andreynaz4renko.commands;

import ru.andreynaz4renko.data.repositories.TaskRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

/**
 * Класс NewCommand представляет команду для создания новой задачи и добавления её в репозиторий.
 * Эта команда позволяет пользователю ввести данные для новой задачи и добавить её в репозиторий.
 */
public class NewCommand extends RepositoryCommand {

    public NewCommand(TaskRepository repository, Scanner scanner) {
        super(repository);
        help = "new                - Create a new task";
        this.scanner = scanner;
    }

    /**
     * Выполняет команду создания новой задачи и добавления её в репозиторий.
     *
     * @param args Список аргументов команды (игнорируются).
     * @return {@code true}, если новая задача успешно создана и добавлена; в противном случае - {@code false}.
     */
    @Override
    public boolean execute(List<String> args) {
        System.out.print("Enter caption: ");
        String caption = scanner.nextLine();
        System.out.print("Enter description: ");
        String description = scanner.nextLine();
        System.out.print("Enter priority: ");
        int priority = scanner.nextInt();
        System.out.print("Enter deadline: ");
        scanner.nextLine();
        LocalDate deadline = LocalDate.parse(scanner.nextLine());

        if (repository.addTask(repository.getMaxTaskId() + 1,
                caption,
                description,
                priority,
                deadline)) {
            System.out.println("New task added.");
            return true;
        }
        System.out.println("Fail to add a new task.");
        return false;
    }
}

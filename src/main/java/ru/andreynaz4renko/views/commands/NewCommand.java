package ru.andreynaz4renko.views.commands;

import ru.andreynaz4renko.data.Task;
import ru.andreynaz4renko.data.repositories.TaskRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class NewCommand extends RepositoryCommand {

    public NewCommand(TaskRepository repository) {
        super(repository);
        help = "new                - Create a new task";
    }

    @Override
    public boolean execute(List<String> args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Enter caption: ");
            String caption = scanner.nextLine();
            System.out.print("Enter description: ");
            String description = scanner.nextLine();
            System.out.print("Enter priority: ");
            int priority = scanner.nextInt();
            System.out.print("Enter deadline: ");
            scanner.nextLine();
            LocalDate deadline = LocalDate.parse(scanner.nextLine());

            Task task = new Task(repository.getMaxTaskId() + 1,
                    caption,
                    description,
                    priority,
                    deadline);

            if (repository.addTask(task)) {
                System.out.println("New task added.");
                return true;
            }
        }
        System.out.println("Fail to add a new task.");
        return false;
    }
}

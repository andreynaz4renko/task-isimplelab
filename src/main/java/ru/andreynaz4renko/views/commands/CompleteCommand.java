package ru.andreynaz4renko.views.commands;

import ru.andreynaz4renko.data.repositories.TaskRepository;

import java.util.List;

public class CompleteCommand extends RepositoryCommand {

    public CompleteCommand(TaskRepository repository) {
        super(repository);
        help = "complete [id]      - Mark a task as completed";
    }

    @Override
    public boolean execute(List<String> args) {
        if (args == null) {
            System.out.println("The task cannot be marked as completed. " +
                    "Make sure you have entered a task id.");
            return false;
        }
        if (args.size() == 1) {
            String id = args.get(0);
            if (repository.completeTask(Integer.parseInt(id))) {
                System.out.println("Task marked as completed.");
                return true;
            }
            System.out.println("Task not found or already completed.");
        }
        return false;
    }
}

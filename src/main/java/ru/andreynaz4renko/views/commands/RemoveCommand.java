package ru.andreynaz4renko.views.commands;

import ru.andreynaz4renko.data.repositories.TaskRepository;

import java.util.List;

public class RemoveCommand extends RepositoryCommand {

    public RemoveCommand(TaskRepository repository) {
        super(repository);
        help = "remove [id]        - Remove a task";
    }

    @Override
    public boolean execute(List<String> args) {
        if (args == null) {
            System.out.println("The task cannot be removed. " +
                    "Make sure you have entered a task id.");
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

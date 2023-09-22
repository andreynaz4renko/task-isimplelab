package ru.andreynaz4renko.views.commands;

import ru.andreynaz4renko.data.TaskStatus;
import ru.andreynaz4renko.data.repositories.TaskRepository;

import java.util.List;

public class ListCommand extends RepositoryCommand {

    public ListCommand(TaskRepository repository) {
        super(repository);
        help = "list [-s [status]] - List tasks. Status can be one of: NEW, IN_PROGRESS, DONE";
    }

    @Override
    public boolean execute(List<String> args) {
        String status;
        if (args != null) {
            if (args.size() == 2) {
                status = args.get(1);
            } else if (args.size() == 1) {
                status = null;
            } else {
                status = "";
            }
        } else {
            status = "";
        }
        if (status == null) {
            System.out.println("The list of tasks cannot be displayed. Check that you entered a status from the range.");
            return false;
        }
        try {
            repository.getTasks().stream()
                    .filter(task -> status.isEmpty() || task.getStatus() == TaskStatus.valueOf(status.toUpperCase()))
                    .forEach(System.out::println);
        } catch (Exception e) {
            System.out.println("The list of tasks cannot be displayed. Check that you entered a status from the range.");
        }
        return false;
    }
}

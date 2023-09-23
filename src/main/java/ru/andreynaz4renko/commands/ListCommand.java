package ru.andreynaz4renko.commands;

import ru.andreynaz4renko.data.TaskStatus;
import ru.andreynaz4renko.data.repositories.TaskRepository;

import java.util.List;

/**
 * Класс ListCommand представляет команду для отображения списка задач из репозитория.
 * Эта команда позволяет отфильтровать и отобразить задачи в зависимости от их статуса.
 */
public class ListCommand extends RepositoryCommand {

    public ListCommand(TaskRepository repository) {
        super(repository);
        help = "list [-s [status]] - List tasks. Status can be one of: NEW, IN_PROGRESS, DONE";
        argsCountList = List.of(0, 2);
    }

    /**
     * Выполняет команду отображения списка задач в зависимости от указанного статуса.
     *
     * @param args Список аргументов команды. Может содержать статус задачи для фильтрации ({@link TaskStatus}).
     * @return {@code true}, если список задач успешно отображен, в противном случае - {@code false}.
     * @see TaskStatus
     */
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
            return true;
        } catch (Exception e) {
            System.out.println("The list of tasks cannot be displayed. Check that you entered a status from the range.");
        }
        return false;
    }
}

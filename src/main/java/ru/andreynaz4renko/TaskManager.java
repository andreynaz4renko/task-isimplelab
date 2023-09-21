package ru.andreynaz4renko;

import com.sun.istack.NotNull;
import ru.andreynaz4renko.data.Task;
import ru.andreynaz4renko.data.TaskStatus;
import ru.andreynaz4renko.data.exceptions.InvalidCaptionException;
import ru.andreynaz4renko.data.exceptions.InvalidPriorityException;
import ru.andreynaz4renko.data.repositories.TaskRepository;

import java.time.LocalDate;
import java.util.Scanner;

public class TaskManager {

    private final TaskRepository taskRepository;

    public TaskManager(@NotNull TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public void run() {
        try (Scanner scanner = new Scanner(System.in)) {
            boolean exit = false;
            while (!exit) {
                clearScreen();
                System.out.print(" > ");
                String[] commandArgs = scanner.nextLine().split(" ");

                if (commandArgs.length == 0 || commandArgs[0].isEmpty()) {
                    continue;
                }

                switch (commandArgs[0]) {
                    case "help":
                        printHelp();
                        break;
                    case "list":
                        if (commandArgs.length == 3 && commandArgs[1].equals("-s")) {
                            listTasks(commandArgs[2]);
                            break;
                        }
                        if (commandArgs.length == 1) {
                            listTasks();
                            break;
                        }
                        printInvalidCommand();
                        break;
                    case "complete":
                        if (commandArgs.length == 2) {
                            completeTask(commandArgs[1]);
                            break;
                        }
                        printInvalidCommand();
                        break;
                    case "new":
                        if (commandArgs.length == 1) {
                            createTask(scanner);
                            break;
                        }
                        printInvalidCommand();
                        break;
                    case "edit":
                        if (commandArgs.length == 2) {
                            editTask(commandArgs[1], scanner);
                            break;
                        }
                        printInvalidCommand();
                        break;
                    case "remove":
                        if (commandArgs.length == 2) {
                            removeTask(commandArgs[1]);
                            break;
                        }
                        printInvalidCommand();
                        break;
                    case "load":
                        loadTasks();
                        break;
                    case "save":
                        saveTasks();
                        break;
                    case "exit":
                        exit = true;
                        break;
                    default:
                        printInvalidCommand();
                        break;
                }
            }
        } catch (Exception e) {
            System.err.println(e.getLocalizedMessage());
        }
    }

    private void printInvalidCommand() {
        System.out.println("Invalid command. Use 'help' for a list of commands.");
    }

    private void loadTasks() {
        if (taskRepository.loadTasks()) {
            System.out.println("Tasks successfully loaded.");
            return;
        }
        System.out.println("Fail to load tasks.");
    }

    private void saveTasks() {
        if (taskRepository.saveTasks()) {
            System.out.println("Tasks successfully saved.");
            return;
        }
        System.out.println("Fail to save tasks.");
    }

    private void printHelp() {
        System.out.println("Available commands:");
        System.out.println("help               - Show this help message");
        System.out.println("list [-s [status]] - List tasks. Status can be one of: NEW, IN_PROGRESS, DONE");
        System.out.println("new                - Create a new task");
        System.out.println("edit [id]          - Edit an existing task");
        System.out.println("remove [id]        - Remove a task");
        System.out.println("complete [id]      - Mark a task as completed");
        System.out.println("load               - Load tasks from repository");
        System.out.println("save               - Save tasks into repository");
        System.out.println("exit               - Exit the program");
    }

    private void listTasks() {
        listTasks(null);
    }

    private void listTasks(String status) {
        System.out.println("Tasks:");
        try {
            taskRepository.getTasks().stream()
                    .filter(task -> status == null || status.isEmpty() ||
                            task.getStatus() == TaskStatus.valueOf(status.toUpperCase()))
                    .forEach(System.out::println);
        } catch (Exception e) {
            System.err.println("The list of tasks cannot be displayed. " +
                    "Check that you entered a status from the range. " + e.getLocalizedMessage());
        }
    }

    private void completeTask(String id) {
        try {
            if (taskRepository.completeTask(Integer.parseInt(id))) {
                System.out.println("Task marked as completed.");
                return;
            }
            System.out.println("Task not found or already completed.");
        } catch (Exception e) {
            System.err.println("The task cannot be marked as completed. " +
                    "Make sure you have entered a number as a task id. " + e.getLocalizedMessage());
        }
    }

    private void createTask(Scanner scanner) throws InvalidPriorityException, InvalidCaptionException {
        System.out.print("Enter caption: ");
        String caption = scanner.nextLine();
        System.out.print("Enter description: ");
        String description = scanner.nextLine();
        System.out.print("Enter priority: ");
        int priority = scanner.nextInt();
        System.out.print("Enter deadline: ");
        scanner.nextLine();
        LocalDate deadline = LocalDate.parse(scanner.nextLine());

        Task task = new Task(taskRepository.getMaxTaskId() + 1,
                caption,
                description,
                priority,
                deadline);

        if (taskRepository.addTask(task)) {
            System.out.println("New task added.");
            return;
        }
        System.out.println("Fail to add a new task.");
    }

    private void editTask(String id, Scanner scanner) {
        try {
            if (taskRepository.isTaskExists(Integer.parseInt(id))) {
                System.out.print("Enter new caption (leave empty to keep current): ");
                String title = scanner.nextLine();
                System.out.print("Enter new description (leave empty to keep current): ");
                String description = scanner.nextLine();
                System.out.print("Enter new priority (leave empty to keep current): ");
                String priority = scanner.nextLine();
                System.out.print("Enter new deadline (leave empty to keep current): ");
                String deadline = scanner.nextLine();

                if (taskRepository.editTask(Integer.parseInt(id), title, description, priority, deadline)) {
                    System.out.println("Task edited.");
                    return;
                }
                System.out.println("Fail to edit task.");
            }
            System.out.println("Task not found.");
        } catch (Exception e) {
            System.err.println("The task cannot be edited. " +
                    "Make sure you have entered a number as a task id. " + e.getLocalizedMessage());
        }
    }

    private void removeTask(String id) {
        try {
            if (taskRepository.removeTask(Integer.parseInt(id))) {
                System.out.println("Task removed.");
                return;
            }
            System.out.println("Fail to remove task.");
        } catch (Exception e) {
            System.err.println("The task cannot be removed. " +
                    "Make sure you have entered a number as a task id. " + e.getLocalizedMessage());
        }
    }

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

}

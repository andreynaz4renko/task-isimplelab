package ru.andreynaz4renko;

import ru.andreynaz4renko.data.TaskRepository;
import ru.andreynaz4renko.domain.Task;
import ru.andreynaz4renko.domain.TaskStatus;

import java.time.LocalDate;
import java.util.Scanner;

public class TaskManager {

    private final TaskRepository taskRepository;

    public TaskManager(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public void run() {
        try (Scanner scanner = new Scanner(System.in)) {
            boolean exit = false;
            while (!exit) {
                clearScreen();
                System.out.print("Enter command:\n> ");
                String[] commandArgs = scanner.nextLine().split(" ");

                if (commandArgs.length == 0) {
                    continue;
                }

                switch (commandArgs[0]) {
                    case "help":
                        printHelp();
                        break;
                    case "list":
                        if (commandArgs.length <= 3 && commandArgs[1].equals("-s")) {
                            listTasks(commandArgs[2]);
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
        System.out.println("help - Show this help message");
        System.out.println("list - List tasks");
        System.out.println("     -s [status (new/in_progress/done)]");
        System.out.println("new - Create a new task");
        System.out.println("edit [id] - Edit an existing task");
        System.out.println("remove [id] - Remove a task");
        System.out.println("complete [id] - Mark a task as completed");
        System.out.println("load - Load tasks from repository");
        System.out.println("save - Save tasks into repository");
        System.out.println("exit - Exit the program");
    }

    private void listTasks(String status) {
        System.out.println("Tasks:");
        taskRepository.getTasksList().getTasks().stream()
                .filter(task -> status == null || status.isEmpty() ||
                        task.getStatus() == TaskStatus.valueOf(status.toUpperCase()))
                .forEach(System.out::println);
    }

    private void completeTask(String taskId) {
        if (taskRepository.completeTask(Integer.parseInt(taskId))) {
            System.out.println("Task marked as completed.");
            return;
        }
        System.out.println("Task not found or already completed.");
    }

    private void createTask(Scanner scanner) {
        System.out.print("Enter caption: ");
        String caption = scanner.nextLine();
        System.out.print("Enter description: ");
        String description = scanner.nextLine();
        System.out.print("Enter priority: ");
        int priority = scanner.nextInt();
        System.out.print("Enter deadline: ");
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

    private void editTask(String taskId, Scanner scanner) {
        if (taskRepository.isTaskExists(Integer.parseInt(taskId))) {
            System.out.print("Enter new title (leave empty to keep current): ");
            String title = scanner.nextLine();
            System.out.print("Enter new description (leave empty to keep current): ");
            String description = scanner.nextLine();
            System.out.print("Enter new priority (leave empty to keep current): ");
            String priority = scanner.nextLine();
            System.out.print("Enter new deadline (leave empty to keep current): ");
            String deadline = scanner.nextLine();

            if (taskRepository.editTask(Integer.parseInt(taskId), title, description, priority, deadline)) {
                System.out.println("Task edited.");
                return;
            }
            System.out.println("Fail to edit task.");
        }
        System.out.println("Task not found.");
    }

    private void removeTask(String taskId) {
        if (taskRepository.removeTask(Integer.parseInt(taskId))) {
            System.out.println("Task removed.");
            return;
        }
        System.out.println("Fail to remove task.");
    }

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

}

package ru.andreynaz4renko;

import com.sun.istack.NotNull;
import ru.andreynaz4renko.data.repositories.TaskRepository;
import ru.andreynaz4renko.views.commands.*;

import java.util.*;

public class CommandManager {
    private final HashMap<String, RepositoryCommand> commands;

    public CommandManager(@NotNull TaskRepository repository) {
        this.commands = new HashMap<>();
        this.commands.put("list",     new ListCommand(repository));
        this.commands.put("new",      new NewCommand(repository));
        this.commands.put("edit",     new EditCommand(repository));
        this.commands.put("remove",   new RemoveCommand(repository));
        this.commands.put("complete", new CompleteCommand(repository));
        this.commands.put("load",     new LoadCommand(repository));
        this.commands.put("save",     new SaveCommand(repository));
    }

    public void run() {
        try (Scanner scanner = new Scanner(System.in)) {
            boolean exit = false;
            while (!exit) {
                clearScreen();
                System.out.print("> ");
                List<String> args = Arrays.stream(scanner.nextLine().split(" ")).toList();

                if (args.isEmpty() || args.get(0).isEmpty()) {
                    continue;
                }

                String command = args.get(0);
                if (command.equals("help")) {
                    System.out.println("Available commands:");
                    System.out.println("help               - Show this help message");
                    System.out.println("exit               - Exit the program");
                    commands.forEach((key, cmd) -> System.out.println(cmd.getHelp()));
                    continue;
                }

                if (command.equals("exit")) {
                    exit = true;
                    continue;
                }

                if (commands.containsKey(command)) {
                    commands.get(command).execute(args.subList(1, args.size()));
                    continue;
                }

                printInvalidCommand();
            }
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
    }

    private void printInvalidCommand() {
        System.out.println("Invalid command. Use 'help' for a list of commands.");
    }

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

}

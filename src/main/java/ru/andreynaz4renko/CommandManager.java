package ru.andreynaz4renko;

import org.jetbrains.annotations.NotNull;
import ru.andreynaz4renko.commands.*;
import ru.andreynaz4renko.data.repositories.TaskRepository;

import java.util.*;

/**
 * Класс CommandManager управляет выполнением команд в консоли.
 * Он предоставляет доступ к различным командам, связанным с репозиторием задач, и выполняет их.
 */
public class CommandManager {
    private final Scanner scanner = new Scanner(System.in);
    private final Map<String, Command> commands;

    /**
     * Конструктор класса CommandManager.
     *
     * @param repository Репозиторий задач, с которым будут работать команды.
     */
    public CommandManager(@NotNull TaskRepository repository) {
        this.commands = new HashMap<>();
        this.commands.put("list",     new ListCommand(repository));
        this.commands.put("new",      new NewCommand(repository, scanner));
        this.commands.put("edit",     new EditCommand(repository, scanner));
        this.commands.put("remove",   new RemoveCommand(repository));
        this.commands.put("complete", new CompleteCommand(repository));
        this.commands.put("load",     new LoadCommand(repository));
        this.commands.put("save",     new SaveCommand(repository));
        this.commands.put("cls",      new ClearConsoleCommand());
    }

    /**
     * Получает команду по её имени.
     *
     * @param name Имя команды.
     * @return Команда, соответствующая заданному имени, или {@code null}, если команда не найдена.
     */
    public Command getCommand(String name) {
        return commands.get(name);
    }

    /**
     * Проверяет, является ли команда допустимой и имеет ли она указанное количество аргументов.
     *
     * @param command   Команда для проверки.
     * @param argsCount Ожидаемое количество аргументов команды.
     * @return {@code true}, если команда не {@code null} и имеет указанное количество аргументов,
     * в противном случае - {@code false}.
     */
    public boolean validateCommand(Command command, int argsCount) {
        return command != null && command.getArgsCountList().contains(argsCount);
    }

    /**
     * Запускает цикл обработки команд в консоли.
     */
    public void run() {
        boolean exit = false;
        while (!exit) {
            try {
                System.out.print("> ");
                List<String> args = Arrays.stream(scanner.nextLine().split(" ")).toList();

                if (args.isEmpty() || args.get(0).isEmpty()) {
                    continue;
                }

                String commandInput = args.get(0);
                if (commandInput.equals("help")) {
                    System.out.println("Available commands:");
                    System.out.println("help               - Show this help message");
                    System.out.println("exit               - Exit the program");
                    commands.values().forEach(command -> System.out.println(command.getHelp()));
                    continue;
                }

                if (commandInput.equals("exit")) {
                    exit = true;
                    continue;
                }

                args = args.subList(1, args.size());

                Command command = getCommand(commandInput);
                if (validateCommand(command, args.size())) {
                    command.execute(args);
                    continue;
                }

                printInvalidCommand();
            } catch (Exception e) {
                System.out.println(e.getLocalizedMessage());
            }
        }
    }

    /**
     * Выводит сообщение об ошибке при вводе недопустимой команды.
     */
    private void printInvalidCommand() {
        System.out.println("Invalid command. Use \"help\" for a list of commands.");
    }

}

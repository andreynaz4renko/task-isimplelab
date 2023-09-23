package ru.andreynaz4renko.commands;

import java.util.List;

/**
 * Интерфейс {@link Command} представляет общий контракт для выполнения команд в приложении.
 * Команды могут быть выполнены как без аргументов, так и с аргументами.
 */
public interface Command {

    /**
     * Выполняет команду без аргументов.
     *
     * @return {@code true}, если команда выполнена успешно, в противном случае - {@code false}.
     */
    boolean execute();

    /**
     * Выполняет команду с переданными аргументами.
     *
     * @param args Список аргументов, необходимых для выполнения команды.
     * @return {@code true}, если команда выполнена успешно, в противном случае - {@code false}.
     */
    boolean execute(List<String> args);

    /**
     * Возвращает требуемое командой количество аргументов.
     *
     * @return Количество аргументов.
     */
    List<Integer> getArgsCountList();

    /**
     * Возвращает помощь по команде.
     *
     * @return Помощь по команде.
     */
    String getHelp();

}

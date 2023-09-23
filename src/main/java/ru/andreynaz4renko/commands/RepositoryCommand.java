package ru.andreynaz4renko.commands;

import ru.andreynaz4renko.data.repositories.TaskRepository;

import java.util.List;
import java.util.Scanner;

/**
 * Абстрактный класс {@link RepositoryCommand} представляет базовый класс для команд,
 * которые оперируют с репозиторием задач {@link TaskRepository}.
 * Классы-команды должны наследоваться от этого класса и реализовывать метод {@link RepositoryCommand#execute(List)}.
 *
 * @see Command
 * @see TaskRepository
 */
public abstract class RepositoryCommand implements Command {

    /**
     * Строка с помощью по команде.
     */
    protected String help;

    /**
     * Требуемое командой количество аргументов.
     */
    protected List<Integer> argsCountList;

    /**
     * Репозиторий задач, с которым взаимодействует команда.
     */
    protected TaskRepository repository;

    protected Scanner scanner;

    /**
     * Конструктор по умолчанию.
     *
     * @param repository Репозиторий задач, с которым будет работать команда.
     * @throws NullPointerException Если переданное значение repository равно {@code null}.
     */
    public RepositoryCommand(TaskRepository repository) throws NullPointerException {
        if (repository == null) {
            throw new NullPointerException("Repository can't be null!");
        }
        this.help = "";
        this.argsCountList = List.of(0);
        this.repository = repository;
        this.scanner = null;
    }

    @Override
    public boolean execute() {
        return execute(null);
    }

    @Override
    public abstract boolean execute(List<String> args);

    @Override
    public List<Integer> getArgsCountList() {
        return argsCountList;
    }

    @Override
    public String getHelp() {
        return help;
    }
}

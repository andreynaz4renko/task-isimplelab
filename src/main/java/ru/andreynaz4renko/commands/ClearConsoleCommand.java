package ru.andreynaz4renko.commands;

import java.util.List;

public class ClearConsoleCommand implements Command {

    @Override
    public boolean execute() {
        return execute(null);
    }

    @Override
    public boolean execute(List<String> args) {
        System.out.print("\033[H\033[2J");
        System.out.flush();
        return true;
    }

    @Override
    public List<Integer> getArgsCountList() {
        return List.of(0);
    }

    @Override
    public String getHelp() {
        return "cls                - Clears console screen";
    }
}

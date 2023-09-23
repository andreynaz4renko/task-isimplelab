package ru.andreynaz4renko.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.andreynaz4renko.CommandManager;
import ru.andreynaz4renko.commands.Command;
import ru.andreynaz4renko.commands.ListCommand;
import ru.andreynaz4renko.data.repositories.TaskRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CommandManagerTest {

    private CommandManager manager;

    @BeforeEach
    public void setUp() {
        manager = new CommandManager(mock(TaskRepository.class));
    }

    @Test
    public void testGetCommand() {
        Command command = manager.getCommand("list");
        assertNotNull(command);
        assertTrue(command instanceof ListCommand);
    }

    @Test
    public void testValidateCommandWithValidCommand() {
        Command command = mock(Command.class);
        when(command.getArgsCountList()).thenReturn(List.of(2));
        assertTrue(manager.validateCommand(command, 2));
    }

    @Test
    public void testValidateCommandWithInvalidCommand() {
        Command command = mock(Command.class);
        when(command.getArgsCountList()).thenReturn(List.of(3));
        assertFalse(manager.validateCommand(command, 2));
    }
}
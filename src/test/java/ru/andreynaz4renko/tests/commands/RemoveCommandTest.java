package ru.andreynaz4renko.tests.commands;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.andreynaz4renko.commands.Command;
import ru.andreynaz4renko.commands.RemoveCommand;
import ru.andreynaz4renko.data.repositories.TaskRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class RemoveCommandTest {

    private TaskRepository repository;
    private Command command;

    @BeforeEach
    public void setUp() {
        repository = mock(TaskRepository.class);
        command = new RemoveCommand(repository);
    }

    @Test
    public void testExecuteWithoutArgs() {
        assertFalse(command.execute());
        assertFalse(command.execute(null));
    }

    @Test
    public void testExecuteWithInvalidArgs() {
        assertFalse(command.execute(List.of("abc")));
    }

    @Test
    public void testExecuteTaskNotFound() {
        when(repository.removeTask(1)).thenReturn(false);
        assertFalse(command.execute(List.of("1")));
    }

    @Test
    public void testExecuteSuccess() {
        when(repository.removeTask(2)).thenReturn(true);
        assertTrue(command.execute(List.of("2")));
    }

}


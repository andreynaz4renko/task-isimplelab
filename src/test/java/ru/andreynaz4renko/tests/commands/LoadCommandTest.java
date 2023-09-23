package ru.andreynaz4renko.tests.commands;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.andreynaz4renko.commands.Command;
import ru.andreynaz4renko.commands.LoadCommand;
import ru.andreynaz4renko.data.repositories.TaskRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class LoadCommandTest {

    private TaskRepository repository;
    private Command command;

    @BeforeEach
    public void setUp() {
        repository = mock(TaskRepository.class);
        command = new LoadCommand(repository);
    }

    @Test
    public void testExecuteSuccess() {
        when(repository.loadTasks()).thenReturn(true);
        assertTrue(command.execute());
    }

    @Test
    public void testExecuteFailure() {
        when(repository.loadTasks()).thenReturn(false);
        assertFalse(command.execute());
    }

    @Test
    public void testExecuteWithArgsSuccess() {
        when(repository.loadTasks()).thenReturn(true);
        assertTrue(command.execute(null));
        assertTrue(command.execute(List.of("1", "2", "3")));
    }

    @Test
    public void testExecuteWithArgsFailure() {
        when(repository.loadTasks()).thenReturn(false);
        assertFalse(command.execute(null));
        assertFalse(command.execute(List.of("1", "2", "3")));
    }

}

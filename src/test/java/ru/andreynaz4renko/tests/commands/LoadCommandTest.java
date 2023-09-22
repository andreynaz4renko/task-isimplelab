package ru.andreynaz4renko.tests.commands;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.andreynaz4renko.data.repositories.TaskRepository;
import ru.andreynaz4renko.views.commands.LoadCommand;
import ru.andreynaz4renko.views.commands.RepositoryCommand;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class LoadCommandTest extends CommandTest {
    private TaskRepository repository;
    private RepositoryCommand loadCommand;

    @BeforeEach
    public void setUp() {
        repository = mock(TaskRepository.class);
        loadCommand = new LoadCommand(repository);
    }

    @Test
    public void testExecuteSuccess() {
        when(repository.loadTasks()).thenReturn(true);
        assertTrue(loadCommand.execute());
    }

    @Test
    public void testExecuteFailure() {
        when(repository.loadTasks()).thenReturn(false);
        assertFalse(loadCommand.execute());
    }

    @Test
    public void testExecuteWithArgs() {
        List<String> args = Arrays.asList("1", "2", "3");

        when(repository.loadTasks()).thenReturn(true);
        assertTrue(loadCommand.execute(null));
        assertTrue(loadCommand.execute(args));

        when(repository.loadTasks()).thenReturn(false);
        assertFalse(loadCommand.execute(null));
        assertFalse(loadCommand.execute(args));
    }

}

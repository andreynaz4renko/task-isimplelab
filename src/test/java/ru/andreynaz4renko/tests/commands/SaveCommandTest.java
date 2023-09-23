package ru.andreynaz4renko.tests.commands;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.andreynaz4renko.commands.Command;
import ru.andreynaz4renko.commands.SaveCommand;
import ru.andreynaz4renko.data.repositories.TaskRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SaveCommandTest {

    private TaskRepository repository;
    private Command command;

    @BeforeEach
    public void setUp() {
        repository = mock(TaskRepository.class);
        command = new SaveCommand(repository);
    }

    @Test
    public void testExecuteSuccess() {
        when(repository.saveTasks()).thenReturn(true);
        assertTrue(command.execute());
    }

    @Test
    public void testExecuteFailure() {
        when(repository.saveTasks()).thenReturn(false);
        assertFalse(command.execute());
    }

    @Test
    public void testExecuteWithArgsSuccess() {
        when(repository.saveTasks()).thenReturn(true);
        assertTrue(command.execute(null));
        assertTrue(command.execute(List.of("1", "2", "3")));
    }

    @Test
    public void testExecuteWithArgsFailure() {
        when(repository.saveTasks()).thenReturn(false);
        assertFalse(command.execute(null));
        assertFalse(command.execute(List.of("1", "2", "3")));
    }

}

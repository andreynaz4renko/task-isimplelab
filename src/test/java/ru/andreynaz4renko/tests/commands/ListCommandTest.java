package ru.andreynaz4renko.tests.commands;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.andreynaz4renko.commands.Command;
import ru.andreynaz4renko.commands.ListCommand;
import ru.andreynaz4renko.data.Task;
import ru.andreynaz4renko.data.TaskStatus;
import ru.andreynaz4renko.data.repositories.TaskRepository;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ListCommandTest {

    private TaskRepository repository;
    private Command command;

    @BeforeEach
    public void setUp() {
        repository = mock(TaskRepository.class);
        command = new ListCommand(repository);
    }

    @Test
    public void testExecuteWithoutArgs() {
        assertTrue(command.execute());
        assertTrue(command.execute(null));
    }

    @Test
    public void testExecuteWithInvalidArgs() {
        assertFalse(command.execute(List.of("abc")));
    }

    @Test
    public void testExecuteWithValidStatus() {
        Task task1 = new Task(1, "Task 1", "Description 1", 1, LocalDate.now(), LocalDate.now(), TaskStatus.NEW);
        Task task2 = new Task(2, "Task 2", "Description 2", 2, LocalDate.now(), LocalDate.now(), TaskStatus.IN_PROGRESS);
        Task task3 = new Task(3, "Task 3", "Description 3", 3, LocalDate.now(), LocalDate.now(), TaskStatus.DONE);
        List<Task> mockTasks = Arrays.asList(task1, task2, task3);
        when(repository.getTasks()).thenReturn(mockTasks);
        assertTrue(command.execute(List.of("-s", "new")));
        assertTrue(command.execute(List.of("-s", "in_progress")));
        assertTrue(command.execute(List.of("-s", "done")));
        assertFalse(command.execute(List.of("-s", "abc")));
    }

}

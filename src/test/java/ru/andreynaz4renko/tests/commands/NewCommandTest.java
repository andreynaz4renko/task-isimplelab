package ru.andreynaz4renko.tests.commands;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.andreynaz4renko.commands.Command;
import ru.andreynaz4renko.commands.NewCommand;
import ru.andreynaz4renko.data.repositories.TaskRepository;

import java.io.ByteArrayInputStream;
import java.time.LocalDate;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class NewCommandTest {

    private TaskRepository repository;
    private Command command;
    private Scanner scanner = new Scanner(System.in);

    @BeforeEach
    public void setUp() {
        repository = mock(TaskRepository.class);
        command = new NewCommand(repository, scanner);
    }

    @Test
    public void testExecute() {
        setupInput("New Task\nTask Description\n3\n2023-12-31");

        when(repository.getMaxTaskId()).thenReturn(0);
        when(repository.addTask(1,
                "New Task",
                "Task Description",
                3,
                LocalDate.parse("2023-12-31"))).thenReturn(true);

        setupInput("New Task\nTask Description\n3\n2023-12-31");

        assertTrue(command.execute());

        setupInput("New Task\nTask Description\n3\n2023-12-31");

        assertTrue(command.execute(null));
    }

    @Test
    public void testExecuteFailure() {
        setupInput("New Task\nTask Description\n3\n2023-12-31");

        when(repository.getMaxTaskId()).thenReturn(0);
        when(repository.addTask(1,
                "New Task",
                "Task Description",
                3,
                LocalDate.parse("2023-12-31"))).thenReturn(false);

        assertFalse(command.execute());

        setupInput("New Task\nTask Description\n3\n2023-12-31");

        assertFalse(command.execute(null));
    }

    private void setupInput(String input) {
        scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        command = new NewCommand(repository, scanner);
    }

}

package ru.andreynaz4renko.tests.commands;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.andreynaz4renko.commands.Command;
import ru.andreynaz4renko.commands.EditCommand;
import ru.andreynaz4renko.data.repositories.TaskRepository;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class EditCommandTest {

    private TaskRepository repository;
    private Command command;
    private Scanner scanner = new Scanner(System.in);

    @BeforeEach
    public void setUp() {
        repository = mock(TaskRepository.class);
        command = new EditCommand(repository, scanner);
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
        when(repository.isTaskExists(1)).thenReturn(false);
        assertFalse(command.execute(List.of("1")));
    }

    @Test
    public void testExecuteSuccess() {
        when(repository.isTaskExists(2)).thenReturn(true);
        when(repository.editTask(2,
                "New Title",
                "New Description",
                "5",
                "2023-12-31")).thenReturn(true);

        setupInput("New Title\nNew Description\n5\n2023-12-31\n");

        assertTrue(command.execute(List.of("2")));

        when(repository.isTaskExists(2)).thenReturn(true);
        when(repository.editTask(2, "", "", "", "")).thenReturn(true);

        setupInput("\n\n\n\n");

        assertTrue(command.execute(List.of("2")));
    }

    private void setupInput(String input) {
        scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        command = new EditCommand(repository, scanner);
    }

}

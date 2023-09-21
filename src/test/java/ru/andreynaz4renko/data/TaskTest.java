package ru.andreynaz4renko.data;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.andreynaz4renko.data.exceptions.InvalidCaptionException;
import ru.andreynaz4renko.data.exceptions.InvalidPriorityException;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TaskTest {

    private Task task;

    @BeforeEach
    public void setUp() throws InvalidPriorityException, InvalidCaptionException {
        task = new Task(1, "Test Task", "Description", 5, LocalDate.now(), LocalDate.now(), TaskStatus.NEW);
    }

    @Test
    public void testSetters() {
        task.setCaption("New Caption");
        assertEquals("New Caption", task.getCaption());

        task.setPriority(7);
        assertEquals(7, task.getPriority());

        task.setDescription("New Description");
        assertEquals("New Description", task.getDescription());

        LocalDate newCompletionDate = LocalDate.now().plusDays(3);
        task.setCompletion(newCompletionDate);
        assertEquals(newCompletionDate, task.getCompletion());

        LocalDate newDeadline = LocalDate.now().plusDays(5);
        task.setDeadline(newDeadline);
        assertEquals(newDeadline, task.getDeadline());

        task.setStatus(TaskStatus.IN_PROGRESS);
        assertEquals(TaskStatus.IN_PROGRESS, task.getStatus());
    }

    @Test
    public void testGetId() {
        assertEquals(1, task.getId());
    }

    @Test
    public void testPriorityException() {
        assertThrows(InvalidPriorityException.class, () -> task.setPriority(-1));

        assertThrows(InvalidPriorityException.class, () -> task.setPriority(11));

        assertThrows(InvalidPriorityException.class, () ->
                new Task(0,
                        "Test",
                        null,
                        -50,
                        null,
                        null,
                        TaskStatus.NEW));

        assertThrows(InvalidPriorityException.class, () ->
                new Task(0,
                        "Test",
                        null,
                        110,
                        null,
                        null,
                        TaskStatus.NEW));
    }

    @Test
    public void testCaptionException() {
        assertThrows(InvalidCaptionException.class, () -> task.setCaption(null));

        assertThrows(InvalidCaptionException.class, () ->
                task.setCaption("This is a very long caption that exceeds the 50 characters limit"));

        assertThrows(InvalidCaptionException.class, () ->
                new Task(0,
                        null,
                        null,
                        1,
                        null,
                        null,
                        TaskStatus.NEW));

        assertThrows(InvalidCaptionException.class, () ->
                new Task(0,
                        "This is a very long caption that exceeds the 50 characters limit",
                        null,
                        1,
                        null,
                        null,
                        TaskStatus.NEW));
    }

    @Test
    public void testToStringTest() {
        String expected = "Task ID: 1\n" +
                "Caption: Test Task\n" +
                "Description: Description\n" +
                "Priority: 5\n" +
                "Deadline: " + LocalDate.now() + "\n" +
                "Completion Date: " + LocalDate.now() + "\n" +
                "Status: NEW";
        assertEquals(expected, task.toString());
    }
}

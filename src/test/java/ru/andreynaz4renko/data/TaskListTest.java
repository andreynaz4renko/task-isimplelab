package ru.andreynaz4renko.data;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TaskListTest {

    private TaskList taskList;
    private Task task1;
    private Task task2;

    @BeforeEach
    public void setUp() {
        taskList = new TaskList();
        task1 = new Task(1, "Task 1", "Description 1", 5, LocalDate.now());
        task2 = new Task(2, "Task 2", "Description 2", 3, LocalDate.now());
    }

    @Test
    public void testAddTask() {
        assertTrue(taskList.addTask(task1));
        assertEquals(1, taskList.getTasks().size());
        assertTrue(taskList.getTasks().contains(task1));
    }

    @Test
    public void testRemoveTask() {
        taskList.addTask(task1);
        taskList.addTask(task2);

        assertTrue(taskList.removeTask(1));
        assertEquals(1, taskList.getTasks().size());
        assertFalse(taskList.getTasks().contains(task1));
        assertTrue(taskList.getTasks().contains(task2));
    }

    @Test
    public void testCompleteTask() {
        taskList.addTask(task1);

        assertNotSame(task1.getStatus(), TaskStatus.DONE);
        assertTrue(taskList.completeTask(1));
        assertSame(task1.getStatus(), TaskStatus.DONE);
        assertNotNull(task1.getCompletion());
    }

    @Test
    public void testGetTasks() {
        List<Task> initialTasks = new ArrayList<>();
        initialTasks.add(task1);
        initialTasks.add(task2);

        TaskList taskListWithInitialTasks = new TaskList(initialTasks);

        List<Task> retrievedTasks = taskListWithInitialTasks.getTasks();
        assertNotNull(retrievedTasks);
        assertEquals(2, retrievedTasks.size());
        assertTrue(retrievedTasks.contains(task1));
        assertTrue(retrievedTasks.contains(task2));
    }
}

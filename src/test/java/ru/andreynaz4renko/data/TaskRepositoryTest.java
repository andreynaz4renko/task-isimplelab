package ru.andreynaz4renko.data;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import ru.andreynaz4renko.data.repositories.RestTaskRepository;
import ru.andreynaz4renko.data.repositories.TaskRepository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static ru.andreynaz4renko.data.RepositoryProvider.JSON_FILEPATH;
import static ru.andreynaz4renko.data.RepositoryProvider.XML_FILEPATH;

public class TaskRepositoryTest {

    private static final String JSON_CONTENT = "{\n" +
            "  \"ToDoList\": [\n" +
            "    {\n" +
            "      \"id\": 1,\n" +
            "      \"caption\": \"Test Task 1\",\n" +
            "      \"description\": \"Description 1\",\n" +
            "      \"priority\": 5,\n" +
            "      \"deadline\": \"" + LocalDate.now() + "\",\n" +
            "      \"completion\": null,\n" +
            "      \"status\": \"new\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": 2,\n" +
            "      \"caption\": \"Test Task 2\",\n" +
            "      \"description\": \"Description 2\",\n" +
            "      \"priority\": 3,\n" +
            "      \"deadline\": \"" + LocalDate.now() + "\",\n" +
            "      \"completion\": null,\n" +
            "      \"status\": \"new\"\n" +
            "    }\n" +
            "  ]\n" +
            "}";

    private static final String XML_CONTENT = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
            "<ToDoList>\n" +
            "    <Task id=\"1\" caption=\"Test Task 1\">\n" +
            "        <Description>Description 1</Description>\n" +
            "        <Priority>5</Priority>\n" +
            "        <Deadline>" + LocalDate.now() + "</Deadline>\n" +
            "        <Complete>null</Complete>\n" +
            "        <Status>NEW</Status>\n" +
            "    </Task>\n" +
            "    <Task id=\"2\" caption=\"Test Task 2\">\n" +
            "        <Description>Description 2</Description>\n" +
            "        <Priority>3</Priority>\n" +
            "        <Deadline>" + LocalDate.now() + "</Deadline>\n" +
            "        <Status>NEW</Status>\n" +
            "    </Task>\n" +
            "</ToDoList>";

    private Task task1;
    private Task task2;
    private Task task3;

    @BeforeEach
    public void setUp() throws IOException {
        createTestFiles();
        task1 = new Task(1, "Test Task 1", "Description 1", 5, LocalDate.now());
        task2 = new Task(2, "Test Task 2", "Description 2", 3, LocalDate.now());
        task3 = new Task(3, "Test Task 3", "Description 3", 7, LocalDate.now());
    }

    @AfterEach
    public void cleanup() {
        deleteTestFile(XML_FILEPATH);
        deleteTestFile(JSON_FILEPATH);
    }

    @ParameterizedTest
    @ArgumentsSource(RepositoryProvider.class)
    public void testAddTasks(TaskRepository repository) {
        assertTrue(repository.addTask(task1));
        assertTrue(repository.addTask(task2));
    }

    @ParameterizedTest
    @ArgumentsSource(RepositoryProvider.class)
    public void testRemoveTasks(TaskRepository repository) {
        repository.addTask(task1);
        repository.addTask(task2);

        assertTrue(repository.removeTask(task1.getId()));
        assertFalse(repository.isTaskExists(task1.getId()));
        assertTrue(repository.removeTask(task2.getId()));
        assertFalse(repository.isTaskExists(task2.getId()));
    }

    @ParameterizedTest
    @ArgumentsSource(RepositoryProvider.class)
    public void testCompleteTask(TaskRepository repository) {
        repository.addTask(task1);

        assertNotSame(task1.getStatus(), TaskStatus.DONE);
        assertTrue(repository.completeTask(1));
        assertSame(task1.getStatus(), TaskStatus.DONE);
        assertNotNull(task1.getCompletion());
    }

    @ParameterizedTest
    @ArgumentsSource(RepositoryProvider.class)
    public void testEditTask(TaskRepository repository) {
        repository.addTask(task1);
        repository.addTask(task2);

        assertTrue(repository.editTask(task1.getId(),
                "New Caption",
                "New Description",
                "5",
                "2000-12-12"));

        Task task = repository.getTaskById(task1.getId());
        assertNotNull(task);
        assertSame(task.getCaption(), task1.getCaption());
        assertNotSame(task.getCaption(), task2.getCaption());

        String task2Caption = String.valueOf(task2.getCaption());
        String task2Description = String.valueOf(task2.getDescription());
        assertTrue(repository.editTask(task2.getId(),
                "",
                "",
                "5",
                "2000-12-12"));

        task = repository.getTaskById(task2.getId());
        assertNotNull(task);
        assertSame(task.getCaption(), task2Caption);
        assertSame(task.getDescription(), task2Description);
        assertNotSame(task.getDeadline(), LocalDate.now());
    }

    @ParameterizedTest
    @ArgumentsSource(RepositoryProvider.class)
    public void testGetTaskById(TaskRepository repository) {
        repository.addTask(task1);
        repository.addTask(task2);
        repository.addTask(task3);

        assertSame(task1, repository.getTaskById(task1.getId()));
        assertSame(task2, repository.getTaskById(task2.getId()));
        assertSame(task3, repository.getTaskById(task3.getId()));
        assertNull(repository.getTaskById(20000));
    }

    @ParameterizedTest
    @ArgumentsSource(RepositoryProvider.class)
    public void testGetTasks(TaskRepository repository) {
        repository.addTask(task1);
        repository.addTask(task2);

        List<Task> retrievedTasks = repository.getTasks();
        assertNotNull(retrievedTasks);
        assertEquals(2, retrievedTasks.size());
        assertTrue(retrievedTasks.contains(task1));
        assertTrue(retrievedTasks.contains(task2));
    }

    @ParameterizedTest
    @ArgumentsSource(RepositoryProvider.class)
    public void testLoadTasks(TaskRepository repository) {
        assertTrue(repository.loadTasks());

        List<Task> loadedTasks = repository.getTasks();
        assertNotNull(loadedTasks);
        assertEquals(2, loadedTasks.size());
    }

    @ParameterizedTest
    @ArgumentsSource(RepositoryProvider.class)
    public void testSaveTasks(TaskRepository repository) {
        if (repository instanceof RestTaskRepository) {
            // TODO: Убрать блок при возможности проверить POST
            return;
        }

        repository.addTask(task1);
        repository.addTask(task2);

        assertTrue(repository.saveTasks());
        assertTrue(repository.loadTasks());

        List<Task> loadedTasks = repository.getTasks();
        assertNotNull(loadedTasks);
        assertEquals(2, loadedTasks.size());

        repository.addTask(task3);
        assertTrue(repository.saveTasks());
        assertTrue(repository.loadTasks());

        loadedTasks = repository.getTasks();
        assertNotNull(loadedTasks);
        assertEquals(3, loadedTasks.size());
        assertTrue(repository.isTaskExists(task3.getId()));
    }

    private void createTestFiles() throws IOException {
        Files.write(Paths.get(XML_FILEPATH), XML_CONTENT.getBytes());
        Files.write(Paths.get(JSON_FILEPATH), JSON_CONTENT.getBytes());
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private void deleteTestFile(String filepath) {
        File file = new File(filepath);
        if (file.exists()) {
            file.delete();
        }
    }
}


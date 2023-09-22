package ru.andreynaz4renko.data.repositories;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sun.istack.NotNull;
import org.apache.commons.io.FileUtils;
import ru.andreynaz4renko.data.TaskList;

import java.io.File;
import java.io.FileWriter;
import java.nio.charset.StandardCharsets;

/**
 * Класс {@link JsonTaskRepository} представляет собой репозиторий задач,
 * способный сохранять и загружать задачи на сервер.
 * Он расширяет абстрактный класс {@link TaskRepository}
 * для отправки и загрузки задач на и с удаленного сервера.
 *
 * @see TaskRepository
 */
public class JsonTaskRepository extends TaskRepository {

    /**
     * Путь к файлу, в котором хранятся задачи в формате JSON.
     */
    private final String filepath;

    private final ObjectMapper mapper;

    /**
     * Конструктор класса {@link JsonTaskRepository}.
     * <p>
     * Пример JSON-файла:
     * <pre>{@code
     * {
     *   "ToDoList": [
     *     {
     *       "id": 1,
     *       "caption": "Заголовок задачи",
     *       "description": "Описание задачи",
     *       "priority": 10,
     *       "deadline": "2017-02-12",
     *       "completion": "2017-02-19",
     *       "status": "done"
     *     },
     *     {
     *       "id": 2,
     *       "caption": "Заголовок еще одной задачи",
     *       "description": "Описание еще одной задачи",
     *       "priority": 10,
     *       "deadline": "2022-02-12",
     *       "status": "new"
     *     }
     *   ]
     * }
     * }</pre>
     *
     * @param filepath Путь к файлу, в котором хранятся задачи в формате JSON.
     */
    public JsonTaskRepository(@NotNull String filepath) {
        this.filepath = filepath;
        this.mapper = new ObjectMapper();
        this.mapper.registerModule(new JavaTimeModule());
    }

    /**
     * Загружает задачи из файла в формате JSON и преобразует их в список задач.
     *
     * @return {@code true}, если задачи успешно загружены, в противном случае - {@code false}.
     */
    @Override
    public boolean loadTasks() {
        try {
            File file = new File(filepath);
            tasks = mapper.readerFor(TaskList.class)
                    .readValue(FileUtils.readFileToString(file, StandardCharsets.UTF_8));
            return true;
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
        return false;
    }

    /**
     * Сохраняет список задач в формате JSON в указанный файл.
     *
     * @return {@code true}, если задачи успешно сохранены, в противном случае - {@code false}.
     */
    @Override
    public boolean saveTasks() {
        try (FileWriter writer = new FileWriter(filepath)) {
            writer.write(mapper.writerWithDefaultPrettyPrinter()
                    .writeValueAsString(tasks));
            return true;
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
        return false;
    }
}

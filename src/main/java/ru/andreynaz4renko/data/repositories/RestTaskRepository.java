package ru.andreynaz4renko.data.repositories;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sun.istack.NotNull;
import okhttp3.*;
import ru.andreynaz4renko.data.TaskList;

/**
 * Класс {@link RestTaskRepository} представляет собой репозиторий задач,
 * способный сохранять и загружать задачи в формате JSON.
 * Он расширяет абстрактный класс {@link TaskRepository}
 * для сериализации и десериализации задач в и из формата JSON.
 *
 * @see TaskRepository
 */
public class RestTaskRepository extends TaskRepository {

    /**
     * Адрес эндпоинта, для взаимодействия с задачами.
     */
    private final String url;


    private final OkHttpClient client;

    private final ObjectMapper mapper;

    private static final MediaType JSON = MediaType.get("application/json");


    /**
     * Конструктор класса {@link JsonTaskRepository}.
     *
     * @param url Адрес эндпоинта, для взаимодействия с задачами.
     */
    public RestTaskRepository(@NotNull String url) {
        this.url = url;
        this.client = new OkHttpClient();
        this.mapper = new ObjectMapper();
        this.mapper.registerModule(new JavaTimeModule());
    }

    /**
     * Загружает задачи с сервера и преобразует их в список задач.
     * <p>
     * Пример ответа от сервера:
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
     * @return {@code true}, если задачи успешно загружены, в противном случае - {@code false}.
     */
    @Override
    public boolean loadTasks() {
        try {
            Request request = new Request.Builder()
                    .url(url)
                    .build();
            try (Response response = client.newCall(request).execute()) {
                if (response.body() != null) {
                    tasks = mapper.readerFor(TaskList.class)
                            .readValue(response.body().string());
                    return true;
                }
            }
        } catch (Exception e) {
            System.err.println(e.getLocalizedMessage());
        }
        return false;
    }

    /**
     * Отправляет список задач на эндпоинт используя POST-запрос.
     *
     * @return {@code true}, если задачи успешно сохранены, в противном случае - {@code false}.
     */
    @Override
    public boolean saveTasks() {
        try {
            RequestBody body = RequestBody.create(mapper.writerWithDefaultPrettyPrinter()
                    .writeValueAsString(tasks), JSON);
            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .build();
            try (Response response = client.newCall(request).execute()) {
                if (response.isSuccessful()) {
                    return true;
                }
            }
        } catch (Exception e) {
            System.err.println(e.getLocalizedMessage());
        }
        return false;
    }
}

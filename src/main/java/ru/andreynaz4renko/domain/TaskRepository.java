package ru.andreynaz4renko.domain;

public abstract class TaskRepository {

    protected TaskList tasks;

    public TaskRepository() {
        tasks = new TaskList();
    }

    abstract boolean loadTasks();
    abstract boolean saveTasks();
    boolean addTask(Task task) {
        return tasks.addTask(task);
    }
    abstract boolean editTask(int id);
    boolean removeTask(int id) {
        return tasks.removeTask(id);
    }
    boolean completeTask(int id) {
        return tasks.completeTask(id);
    }

    abstract TaskList getTasks();

}

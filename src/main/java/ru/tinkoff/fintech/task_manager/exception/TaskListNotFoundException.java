package ru.tinkoff.fintech.task_manager.exception;

public class TaskListNotFoundException extends RuntimeException {

    public TaskListNotFoundException() {
        super("List of tasks not found");
    }
}

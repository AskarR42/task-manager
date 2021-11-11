package ru.tinkoff.fintech.task_manager.exception;

public class TaskAlreadyExistsException extends RuntimeException {

    public TaskAlreadyExistsException() {
        super("Task already exists");
    }
}

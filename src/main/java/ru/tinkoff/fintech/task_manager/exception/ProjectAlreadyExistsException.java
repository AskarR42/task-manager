package ru.tinkoff.fintech.task_manager.exception;

public class ProjectAlreadyExistsException extends RuntimeException {

    public ProjectAlreadyExistsException() {
        super("Project already exists");
    }
}

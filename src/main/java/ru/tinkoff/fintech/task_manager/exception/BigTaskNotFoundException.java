package ru.tinkoff.fintech.task_manager.exception;

public class BigTaskNotFoundException extends RuntimeException {

    public BigTaskNotFoundException() {
        super("Big task not found");
    }
}

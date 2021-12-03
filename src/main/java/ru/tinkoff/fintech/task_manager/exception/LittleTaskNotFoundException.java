package ru.tinkoff.fintech.task_manager.exception;

public class LittleTaskNotFoundException extends RuntimeException {

    public LittleTaskNotFoundException() {
        super("Little task not found");
    }
}

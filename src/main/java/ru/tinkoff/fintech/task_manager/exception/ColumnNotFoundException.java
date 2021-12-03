package ru.tinkoff.fintech.task_manager.exception;

public class ColumnNotFoundException extends RuntimeException {

    public ColumnNotFoundException() {
        super("Column not found");
    }
}

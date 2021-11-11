package ru.tinkoff.fintech.task_manager.exception;

import com.fasterxml.jackson.annotation.JsonIgnore;

public enum ApplicationError {

    USER_ALREADY_EXISTS("User already exists", 400),
    USER_NOT_FOUND("User not found", 400),
    TASK_ALREADY_EXISTS("Task already exists", 400),
    TASK_NOT_FOUND("Task not found", 400);

    public String message;
    public int code;

    ApplicationError(String message, int code) {
        this.message = message;
        this.code = code;
    }

    public ApplicationException exception(String message) {
        return new ApplicationException(this, message);
    }

    public static class ApplicationException extends RuntimeException {

        public final ApplicationExceptionCompanion companion;

        ApplicationException(ApplicationError error, String message) {
            super(error.message + " : " + message);
            companion = new ApplicationExceptionCompanion(error.message + " : " + message, error.code);
        }
    }

    public static record ApplicationExceptionCompanion(String message, @JsonIgnore int code) {}

}

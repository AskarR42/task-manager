package ru.tinkoff.fintech.task_manager.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@AllArgsConstructor
public class Task {

    @NotNull(message = "Id cannot be null")
    private UUID id;

    @NotEmpty(message = "Text cannot be empty or null")
    private String text;

    private int columnId;

    @NotNull(message = "Project id cannot be null")
    private UUID projectId;
}

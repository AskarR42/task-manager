package ru.tinkoff.fintech.task_manager.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@AllArgsConstructor
public class Project {

    @NotNull(message = "Id cannot be null")
    private UUID id;

    @NotEmpty(message = "Title cannot be empty or null")
    private String title;

    @NotNull(message = "User id cannot be null")
    private UUID userId;
}

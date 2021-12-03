package ru.tinkoff.fintech.task_manager.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskDto {

    private UUID id;

    @NotEmpty(message = "Status cannot be empty or null")
    private String status;

    @NotEmpty(message = "Text cannot be empty or null")
    private String text;

    @NotNull(message = "List of task id cannot be null")
    private UUID listOfTasksId;
}

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
public class LittleTaskDto {

    private UUID id;

    @NotEmpty(message = "Status cannot be empty or null")
    private String status;

    @NotEmpty(message = "Text cannot be empty or null")
    private String text;

    @NotNull(message = "Big task id cannot be null")
    private UUID bigTaskId;
}

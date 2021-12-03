package ru.tinkoff.fintech.task_manager.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskListDto {

    private UUID id;

    private String name;

    @NotNull(message = "Tasks cannot be null")
    private List<TaskDto> tasks;
}

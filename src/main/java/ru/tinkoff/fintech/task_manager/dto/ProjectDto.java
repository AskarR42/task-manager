package ru.tinkoff.fintech.task_manager.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectDto {

    private UUID id;

    @NotEmpty(message = "Name cannot be empty or null")
    private String name;

    private String description;

    private String date;

    private String color;
}

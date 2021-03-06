package ru.tinkoff.fintech.task_manager.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class Project {

    private UUID id;

    private String name;

    private String description;

    private String date;

    private String color;

    private UUID userId;
}

package ru.tinkoff.fintech.task_manager.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class TaskList {

    private UUID id;

    private String name;

    private UUID userId;
}

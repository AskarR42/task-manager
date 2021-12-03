package ru.tinkoff.fintech.task_manager.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
public class Column {

    private int id;

    private String title;

    private List<UUID> tasksIds;
}

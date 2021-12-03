package ru.tinkoff.fintech.task_manager.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class LittleTask {

    private UUID id;

    private String status;

    private String text;

    private UUID bigTaskId;
}

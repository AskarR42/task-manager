package ru.tinkoff.fintech.task_manager.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BigTaskDto {

    private UUID id;

    private TaskInfo info;

    private UUID projectId;

    private int columnId;
}

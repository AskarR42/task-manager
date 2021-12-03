package ru.tinkoff.fintech.task_manager.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

//полное дерьмо а не архитектура
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskInfo {

    private String name;

    private String description;

    private String date;

    private String color;

    List<LittleTaskDto> subTasks;
}

package ru.tinkoff.fintech.task_manager.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
public class Board {

    @NotNull
    @NotEmpty
    private String title;

    @NotNull
    private List<Task> tasks;
}

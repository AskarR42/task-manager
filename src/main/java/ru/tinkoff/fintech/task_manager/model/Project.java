package ru.tinkoff.fintech.task_manager.model;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

public class Project {

    @NotNull
    @NotEmpty
    private String title;

    @NotNull
    private List<Board> boards;
}

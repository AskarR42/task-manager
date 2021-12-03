package ru.tinkoff.fintech.task_manager.dao;

import org.apache.ibatis.annotations.Mapper;
import ru.tinkoff.fintech.task_manager.model.TaskList;
import ru.tinkoff.fintech.task_manager.model.Task;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Mapper
public interface TaskListRepository {

    void save(TaskList taskList);

    List<TaskList> findAll(UUID userId);

    Optional<TaskList> findById(UUID id);

    List<Task> findTasks(TaskList taskList);

    void delete(TaskList taskList);

    void edit(TaskList taskList);
}

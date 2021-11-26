package ru.tinkoff.fintech.task_manager.dao;

import org.apache.ibatis.annotations.Mapper;
import ru.tinkoff.fintech.task_manager.model.Project;
import ru.tinkoff.fintech.task_manager.model.Task;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Mapper
public interface TaskRepository {

    void save(Task task);

    Optional<Task> findById(UUID id);

    List<Task> findAllTasksOfProject(Project project);

    void delete(Task task);

    void edit(Task task);
}

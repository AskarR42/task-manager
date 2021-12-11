package ru.tinkoff.fintech.task_manager.dao;

import org.apache.ibatis.annotations.Mapper;
import ru.tinkoff.fintech.task_manager.model.Task;

import java.util.Optional;
import java.util.UUID;

@Mapper
public interface TaskRepository {

    void save(Task task);

    Optional<Task> findById(UUID id);

    void edit(Task task);

    void delete(Task task);
}

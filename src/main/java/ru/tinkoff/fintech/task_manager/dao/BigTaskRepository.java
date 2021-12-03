package ru.tinkoff.fintech.task_manager.dao;

import org.apache.ibatis.annotations.Mapper;
import ru.tinkoff.fintech.task_manager.model.BigTask;
import ru.tinkoff.fintech.task_manager.model.LittleTask;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Mapper
public interface BigTaskRepository {

    void save(BigTask bigTask);

    Optional<BigTask> findById(UUID id);

    List<LittleTask> findLittleTasks(BigTask bigTask);

    void delete(BigTask bigTask);

    void edit(BigTask bigTask);
}

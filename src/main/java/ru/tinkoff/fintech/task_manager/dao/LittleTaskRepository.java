package ru.tinkoff.fintech.task_manager.dao;

import org.apache.ibatis.annotations.Mapper;
import ru.tinkoff.fintech.task_manager.model.LittleTask;

import java.util.Optional;
import java.util.UUID;

@Mapper
public interface LittleTaskRepository {

    void save(LittleTask littleTask);

    Optional<LittleTask> findById(UUID id);

    void delete(LittleTask littleTask);

    void edit(LittleTask littleTask);
}

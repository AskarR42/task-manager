package ru.tinkoff.fintech.task_manager.dao;

import org.apache.ibatis.annotations.Mapper;
import ru.tinkoff.fintech.task_manager.model.BigTask;
import ru.tinkoff.fintech.task_manager.model.Column;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Mapper
public interface ColumnRepository {

    void save(Column column);

    Optional<Column> findById(UUID id);

    List<BigTask> findBigTask(Column column);

    void delete(Column column);

    void edit(Column column);
}

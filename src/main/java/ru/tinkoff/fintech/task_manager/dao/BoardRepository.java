package ru.tinkoff.fintech.task_manager.dao;

import org.apache.ibatis.annotations.Mapper;
import ru.tinkoff.fintech.task_manager.model.Board;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Mapper
public interface BoardRepository {

    void save(Board board);

    List<Board> findAll();

    Optional<Board> findById(UUID id);

    void delete(Board board);

    void edit(Board board);
}

package ru.tinkoff.fintech.task_manager.dao;

import org.apache.ibatis.annotations.Mapper;
import ru.tinkoff.fintech.task_manager.model.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Mapper
public interface UserRepository {

    void save(User user);

    List<User> findAll();

    Optional<User> findById(UUID id);

    void delete(User user);

    void edit(User user);
}

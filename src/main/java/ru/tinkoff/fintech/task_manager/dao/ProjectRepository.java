package ru.tinkoff.fintech.task_manager.dao;

import org.apache.ibatis.annotations.Mapper;
import ru.tinkoff.fintech.task_manager.model.Project;
import ru.tinkoff.fintech.task_manager.model.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Mapper
public interface ProjectRepository {

    void save(Project project);

    Optional<Project> findById(UUID id);

    List<Project> findAllProjectsOfUser(User user);

    void delete(Project project);

    void edit(Project project);
}

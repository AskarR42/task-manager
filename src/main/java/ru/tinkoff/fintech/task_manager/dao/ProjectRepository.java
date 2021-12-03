package ru.tinkoff.fintech.task_manager.dao;

import org.apache.ibatis.annotations.Mapper;
import ru.tinkoff.fintech.task_manager.model.BigTask;
import ru.tinkoff.fintech.task_manager.model.Project;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Mapper
public interface ProjectRepository {

    void save(Project project);

    List<Project> findAll(UUID userId);

    Optional<Project> findById(UUID id);

    List<BigTask> findBigTasks(Project project);

    void delete(Project project);

    void edit(Project project);
}

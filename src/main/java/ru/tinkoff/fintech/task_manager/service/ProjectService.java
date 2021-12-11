package ru.tinkoff.fintech.task_manager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import ru.tinkoff.fintech.task_manager.dao.ProjectRepository;
import ru.tinkoff.fintech.task_manager.dto.BigTaskDto;
import ru.tinkoff.fintech.task_manager.dto.ProjectDto;
import ru.tinkoff.fintech.task_manager.dto.TaskInfo;
import ru.tinkoff.fintech.task_manager.exception.ProjectNotFoundException;
import ru.tinkoff.fintech.task_manager.model.BigTask;
import ru.tinkoff.fintech.task_manager.model.Column;
import ru.tinkoff.fintech.task_manager.model.Project;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ProjectService {

    ProjectRepository projectRepository;

    UserService userService;
    BigTaskService bigTaskService;

    @Autowired
    public ProjectService(ProjectRepository projectRepository, UserService userService, BigTaskService bigTaskService) {
        this.projectRepository = projectRepository;

        this.userService = userService;
        this.bigTaskService = bigTaskService;
    }

    public void save(ProjectDto projectDto, UUID projectId, Authentication authentication) {
        UUID userId = userService.findCurrentUser(authentication).getId();
        projectRepository.save(new Project(projectId, projectDto.getName(), projectDto.getDescription(), projectDto.getDate(), projectDto.getColor(), userId));
    }

    public List<ProjectDto> findAll(Authentication authentication) {
        UUID userId = userService.findCurrentUser(authentication).getId();
        return projectRepository.findAll(userId)
            .stream()
            .map(project -> new ProjectDto(project.getId(), project.getName(), project.getDescription(), project.getDate(), project.getColor()))
            .toList();
    }

    public List<BigTaskDto> findBigTasks(UUID projectId) {
        Project project = projectRepository.findById(projectId)
            .orElseThrow(ProjectNotFoundException::new);

        return projectRepository.findBigTasks(project)
            .stream()
            .map(bigTask -> new BigTaskDto(
                bigTask.getId(),
                new TaskInfo(
                    bigTask.getName(),
                    bigTask.getDescription(),
                    bigTask.getDate(),
                    bigTask.getColor(),
                    bigTaskService.findLittleTasks(bigTask.getId())
                ),
                bigTask.getProjectId(),
                bigTask.getColumnId()))
            .toList();
    }

    public List<Column> findColumns(UUID projectId) {
        List<BigTaskDto> bigTaskDtos = findBigTasks(projectId);

        List<List<UUID>> bigTaskIds = new ArrayList<>();
        bigTaskIds.add(new ArrayList<>());
        bigTaskIds.add(new ArrayList<>());
        bigTaskIds.add(new ArrayList<>());
        for (BigTaskDto bigTaskDto : bigTaskDtos) {
            bigTaskIds.get(bigTaskDto.getColumnId()).add(bigTaskDto.getId());
        }

        return List.of(new Column(0, "Сделать", bigTaskIds.get(0)), new Column(1, "В процессе", bigTaskIds.get(1)), new Column(2, "Завершены", bigTaskIds.get(2)));
    }

    public void edit(ProjectDto projectDto, Authentication authentication) {
        UUID userId = userService.findCurrentUser(authentication).getId();
        projectRepository.edit(new Project(projectDto.getId(), projectDto.getName(), projectDto.getDescription(), projectDto.getDate(), projectDto.getColor(), userId));
    }

    public void editBigTasks(List<BigTaskDto> bigTaskDtos) {
        for (BigTaskDto bigTaskDto : bigTaskDtos) {
            bigTaskService.edit(bigTaskDto);
        }
    }

    public void delete(UUID projectId) {
        Project project = projectRepository.findById(projectId)
            .orElseThrow(ProjectNotFoundException::new);
        for (BigTask bigTask : projectRepository.findBigTasks(project)) {
            bigTaskService.delete(bigTask.getId());
        }
        projectRepository.delete(project);
    }
}

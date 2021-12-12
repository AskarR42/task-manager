package ru.tinkoff.fintech.task_manager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.tinkoff.fintech.task_manager.dto.BigTaskDto;
import ru.tinkoff.fintech.task_manager.dto.ProjectDto;
import ru.tinkoff.fintech.task_manager.model.Column;
import ru.tinkoff.fintech.task_manager.service.ProjectService;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RequestMapping("/api/project")
@RestController
public class ProjectController {

    ProjectService projectService;

    @Autowired
    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping
    public Map<String, UUID> addProject(@Valid @RequestBody ProjectDto projectDto, Authentication authentication) {
        UUID projectId = UUID.randomUUID();
        projectService.save(projectDto, projectId, authentication);
        return Map.of("id", projectId);
    }

    @GetMapping
    public List<ProjectDto> getAllProjects(Authentication authentication) {
        return projectService.findAll(authentication);
    }

    @GetMapping("/{projectId}")
    public Map<String, List<?>> getColumnsAndBigTasks(@PathVariable UUID projectId) {
        List<Column> columns = projectService.findColumns(projectId);
        List<BigTaskDto> bigTasks = projectService.findBigTasks(projectId);
        return Map.of("columns", columns, "tasks", bigTasks);
    }

    @PutMapping("/{projectId}")
    public void editProjectTasks(@RequestBody List<BigTaskDto> bigTaskDtos) {
        projectService.editBigTasks(bigTaskDtos);
    }

    @PutMapping
    public void editProject(@Valid @RequestBody ProjectDto projectDto, Authentication authentication) {
        projectService.edit(projectDto, authentication);
    }

    @DeleteMapping
    public void deleteProject(@RequestParam UUID id) {
        projectService.delete(id);
    }
}

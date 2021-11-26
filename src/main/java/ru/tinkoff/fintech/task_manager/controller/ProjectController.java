package ru.tinkoff.fintech.task_manager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.tinkoff.fintech.task_manager.exception.ProjectAlreadyExistsException;
import ru.tinkoff.fintech.task_manager.exception.ProjectNotFoundException;
import ru.tinkoff.fintech.task_manager.exception.TaskNotFoundException;
import ru.tinkoff.fintech.task_manager.exception.UserNotFoundException;
import ru.tinkoff.fintech.task_manager.model.Project;
import ru.tinkoff.fintech.task_manager.service.ProjectService;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

import static java.lang.String.format;
import static ru.tinkoff.fintech.task_manager.exception.ApplicationError.PROJECT_ALREADY_EXISTS;
import static ru.tinkoff.fintech.task_manager.exception.ApplicationError.PROJECT_NOT_FOUND;
import static ru.tinkoff.fintech.task_manager.exception.ApplicationError.USER_NOT_FOUND;

@RequestMapping("/project")
@RestController
public class ProjectController {

    private final ProjectService projectService;

    @Autowired
    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping
    public void addProject(@Valid @RequestBody Project project) {
        try {
            projectService.insertNewProject(project);
        } catch (ProjectAlreadyExistsException e) {
            throw PROJECT_ALREADY_EXISTS.exception(format("Project with id=%s already exists", project.getId()));
        }
    }

    @DeleteMapping
    public void deleteProject(@RequestParam UUID id) {
        try {
            projectService.deleteProjectById(id);
        } catch (TaskNotFoundException e) {
            throw PROJECT_NOT_FOUND.exception(format("Project with id=%s not found", id));
        }
    }

    @GetMapping("/{userId}")
    public List<Project> findTasksOfProject(@PathVariable UUID userId) {
        try {
            return projectService.getAllProjectsOfUser(userId);
        } catch (UserNotFoundException e) {
            throw USER_NOT_FOUND.exception(format("User with id=%s not found", userId));
        }
    }

    @GetMapping
    public Project findProject(@RequestParam UUID id) {
        try {
            return projectService.getProjectById(id);
        } catch (ProjectNotFoundException e) {
            throw PROJECT_NOT_FOUND.exception(format("Project with id=%s not found", id));
        }
    }

    @PutMapping
    public void editProject(@Valid @RequestBody Project project) {
        try {
            projectService.editProject(project);
        } catch (ProjectNotFoundException e) {
            throw PROJECT_NOT_FOUND.exception(format("Project with id=%s found", project.getId()));
        }
    }
}

package ru.tinkoff.fintech.task_manager.service;

import org.springframework.beans.factory.annotation.Autowired;
import ru.tinkoff.fintech.task_manager.dao.ProjectRepository;
import ru.tinkoff.fintech.task_manager.dao.UserRepository;
import ru.tinkoff.fintech.task_manager.exception.ProjectAlreadyExistsException;
import ru.tinkoff.fintech.task_manager.exception.ProjectNotFoundException;
import ru.tinkoff.fintech.task_manager.exception.UserNotFoundException;
import ru.tinkoff.fintech.task_manager.model.Project;
import ru.tinkoff.fintech.task_manager.model.User;

import java.util.List;
import java.util.UUID;

public class ProjectService {

    ProjectRepository projectRepository;
    UserRepository userRepository;

    @Autowired
    public ProjectService(ProjectRepository projectRepository, UserRepository userRepository) {
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
    }

    public void insertNewProject(Project project) {
        if (projectRepository.findById(project.getId()).isPresent()) {
            throw new ProjectAlreadyExistsException();
        }
        projectRepository.save(project);
    }

    public Project getProjectById(UUID id) {
        return projectRepository.findById(id).orElseThrow(ProjectNotFoundException::new);
    }

    public List<Project> getAllProjectsOfUser(UUID userId) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

        return projectRepository.findAllProjectsOfUser(user);
    }

    public void deleteProjectById(UUID id) {
        Project project = projectRepository.findById(id).orElseThrow(ProjectNotFoundException::new);
        projectRepository.delete(project);
    }

    public void editProject(Project project) {
        if (projectRepository.findById(project.getId()).isEmpty()) {
            throw new ProjectNotFoundException();
        }
        projectRepository.edit(project);
    }
}

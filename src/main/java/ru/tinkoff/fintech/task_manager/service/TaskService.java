package ru.tinkoff.fintech.task_manager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.tinkoff.fintech.task_manager.dao.ProjectRepository;
import ru.tinkoff.fintech.task_manager.dao.TaskRepository;
import ru.tinkoff.fintech.task_manager.exception.ProjectNotFoundException;
import ru.tinkoff.fintech.task_manager.exception.TaskAlreadyExistsException;
import ru.tinkoff.fintech.task_manager.exception.TaskNotFoundException;
import ru.tinkoff.fintech.task_manager.model.Project;
import ru.tinkoff.fintech.task_manager.model.Task;

import java.util.List;
import java.util.UUID;

@Service
public class TaskService {

    ProjectRepository projectRepository;
    TaskRepository taskRepository;

    @Autowired
    public TaskService(ProjectRepository projectRepository, TaskRepository taskRepository) {
        this.projectRepository = projectRepository;
        this.taskRepository = taskRepository;
    }

    public void insertNewTask(Task task) {
        if (taskRepository.findById(task.getId()).isPresent()) {
            throw new TaskAlreadyExistsException();
        }
        taskRepository.save(task);
    }

    public Task getTaskById(UUID id) {
        return taskRepository.findById(id).orElseThrow(TaskNotFoundException::new);
    }

    public List<Task> getAllTasksOfProject(UUID id) {
        Project project = projectRepository.findById(id).orElseThrow(ProjectNotFoundException::new);

        return taskRepository.findAllTasksOfProject(project);
    }

    public void deleteTaskById(UUID id) {
        Task task = taskRepository.findById(id).orElseThrow(TaskNotFoundException::new);
        taskRepository.delete(task);
    }

    public void editTask(Task task) {
        if (taskRepository.findById(task.getId()).isEmpty()) {
            throw new TaskNotFoundException();
        }
        taskRepository.edit(task);
    }
}

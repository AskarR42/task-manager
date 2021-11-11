package ru.tinkoff.fintech.task_manager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.tinkoff.fintech.task_manager.dao.TaskRepository;
import ru.tinkoff.fintech.task_manager.exception.TaskAlreadyExistsException;
import ru.tinkoff.fintech.task_manager.exception.TaskNotFoundException;
import ru.tinkoff.fintech.task_manager.model.Task;

import java.util.List;
import java.util.UUID;

@Service
public class TaskService {

    TaskRepository taskRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository) {
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

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
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

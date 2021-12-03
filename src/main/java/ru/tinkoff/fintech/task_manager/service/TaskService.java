package ru.tinkoff.fintech.task_manager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.tinkoff.fintech.task_manager.dao.TaskRepository;
import ru.tinkoff.fintech.task_manager.dto.TaskDto;
import ru.tinkoff.fintech.task_manager.exception.TaskNotFoundException;
import ru.tinkoff.fintech.task_manager.model.Task;

import java.util.UUID;

@Service
public class TaskService {

    TaskRepository taskRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public void save(TaskDto taskDto, UUID taskId) {
        taskRepository.save(new Task(taskId, taskDto.getStatus(), taskDto.getText(), taskDto.getListOfTasksId()));
    }

    public TaskDto findById(UUID id) {
        Task task = taskRepository.findById(id)
            .orElseThrow(TaskNotFoundException::new);
        return new TaskDto(task.getId(), task.getStatus(), task.getText(), task.getListOfTasksId());
    }

    public void delete(UUID id) {
        Task task = taskRepository.findById(id)
            .orElseThrow(TaskNotFoundException::new);
        taskRepository.delete(task);
    }

    public void editTask(TaskDto taskDto) {
        if (taskRepository.findById(taskDto.getId())
            .isEmpty()) {
            throw new TaskNotFoundException();
        }
        taskRepository.edit(
            new Task(
                taskDto.getId(),
                taskDto.getStatus(),
                taskDto.getText(),
                taskRepository.findById(taskDto.getId())
                    .get()
                    .getListOfTasksId()
            )
        );
    }
}

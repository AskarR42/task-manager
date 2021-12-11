package ru.tinkoff.fintech.task_manager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import ru.tinkoff.fintech.task_manager.dao.TaskListRepository;
import ru.tinkoff.fintech.task_manager.dao.TaskRepository;
import ru.tinkoff.fintech.task_manager.dto.TaskListDto;
import ru.tinkoff.fintech.task_manager.dto.TaskDto;
import ru.tinkoff.fintech.task_manager.exception.TaskListNotFoundException;
import ru.tinkoff.fintech.task_manager.model.TaskList;
import ru.tinkoff.fintech.task_manager.model.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class TaskListService {

    TaskListRepository taskListRepository;
    TaskRepository taskRepository;

    UserService userService;

    @Autowired
    public TaskListService(TaskListRepository taskListRepository, TaskRepository taskRepository, UserService userService) {
        this.taskListRepository = taskListRepository;
        this.taskRepository = taskRepository;

        this.userService = userService;
    }

    // Task list always has tasks = []
    public void save(TaskListDto taskListDto, UUID taskListId, Authentication authentication) {
        UUID userId = userService.findCurrentUser(authentication).getId();
        taskListRepository.save(new TaskList(taskListId, taskListDto.getName(), userId));
    }

    private List<TaskDto> findTasks(UUID taskListId) {
        TaskList taskList = taskListRepository.findById(taskListId).orElseThrow(TaskListNotFoundException::new);
        List<TaskDto> tasks = new ArrayList<>();
        for (Task task : taskListRepository.findTasks(taskList)) {
            tasks.add(new TaskDto(task.getId(), task.getStatus(), task.getText(), task.getListOfTasksId()));
        }

        return tasks;
    }

    public List<TaskListDto> findAll(Authentication authentication) {
        UUID userId = userService.findCurrentUser(authentication).getId();
        return taskListRepository.findAll(userId).stream().map(taskList -> new TaskListDto(taskList.getId(), taskList.getName(), findTasks(taskList.getId()))).toList();
    }

    public TaskListDto findById(UUID id) {
        TaskList taskList = taskListRepository.findById(id).orElseThrow(TaskListNotFoundException::new);
        List<TaskDto> tasks = findTasks(id);
        return new TaskListDto(taskList.getId(), taskList.getName(), tasks);
    }

    public void delete(UUID id) {
        TaskList taskList = taskListRepository.findById(id).orElseThrow(TaskListNotFoundException::new);
        for (Task task : taskListRepository.findTasks(taskList)) {
            taskRepository.delete(task);
        }
        taskListRepository.delete(taskList);
    }

    public void edit(TaskListDto listOfTasksDto, Authentication authentication) {
        UUID userId = userService.findCurrentUser(authentication).getId();
        if (taskListRepository.findById(listOfTasksDto.getId()).isEmpty()) {
            throw new TaskListNotFoundException();
        }
        taskListRepository.edit(new TaskList(listOfTasksDto.getId(), listOfTasksDto.getName(), userId));
    }
}

package ru.tinkoff.fintech.task_manager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.tinkoff.fintech.task_manager.dto.TaskListDto;
import ru.tinkoff.fintech.task_manager.exception.TaskListNotFoundException;
import ru.tinkoff.fintech.task_manager.service.TaskListService;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static java.lang.String.format;
import static ru.tinkoff.fintech.task_manager.exception.ApplicationError.LIST_OF_TASKS_NOT_FOUND;

@RequestMapping("/api/task_list")
@RestController
public class TaskListController {

    TaskListService taskListService;

    @Autowired
    public TaskListController(TaskListService taskListService) {
        this.taskListService = taskListService;
    }

    @GetMapping
    public List<TaskListDto> getAllTaskList(Authentication authentication) {
        return taskListService.findAll(authentication);
    }

    @PostMapping
    public Map<String, UUID> addTaskList(@Valid @RequestBody TaskListDto taskListDto, Authentication authentication) {
        UUID taskListId = UUID.randomUUID();
        taskListService.save(taskListDto, taskListId, authentication);
        return Map.of("id", taskListId);
    }

    @PutMapping
    public void editTaskList(@Valid @RequestBody TaskListDto taskListDto, Authentication authentication) {
        try {
            taskListService.edit(taskListDto, authentication);
        } catch (TaskListNotFoundException e) {
            throw LIST_OF_TASKS_NOT_FOUND.exception(format("List of tasks with id=%s not found", taskListDto.getId()));
        }
    }

    @DeleteMapping
    public void deleteTaskList(@RequestParam UUID id) {
        try {
            taskListService.delete(id);
        } catch (TaskListNotFoundException e) {
            throw LIST_OF_TASKS_NOT_FOUND.exception(format("List of tasks with id=%s not found", id));
        }
    }
}

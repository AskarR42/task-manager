package ru.tinkoff.fintech.task_manager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.tinkoff.fintech.task_manager.exception.TaskAlreadyExistsException;
import ru.tinkoff.fintech.task_manager.exception.TaskNotFoundException;
import ru.tinkoff.fintech.task_manager.model.Task;
import ru.tinkoff.fintech.task_manager.service.TaskService;

import javax.validation.Valid;

import java.util.UUID;

import static java.lang.String.format;
import static ru.tinkoff.fintech.task_manager.exception.ApplicationError.TASK_ALREADY_EXISTS;
import static ru.tinkoff.fintech.task_manager.exception.ApplicationError.TASK_NOT_FOUND;

@RequestMapping("/task")
@RestController
public class TaskController {

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public void addTask(@Valid @RequestBody Task task) {
        try {
            taskService.insertNewTask(task);
        } catch (TaskAlreadyExistsException e) {
            throw TASK_ALREADY_EXISTS.exception(format("Task with id=%s already exists", task.getId()));
        }
    }

    @DeleteMapping
    public void deleteTask(@RequestParam UUID id) {
        try {
            taskService.deleteTaskById(id);
        } catch (TaskNotFoundException e) {
            throw TASK_NOT_FOUND.exception(format("Task with id=%s not found", id));
        }
    }

    @GetMapping
    public Task findTask(@RequestParam UUID id) {
        try {
            return taskService.getTaskById(id);
        } catch (TaskNotFoundException e) {
            throw TASK_NOT_FOUND.exception(format("Task with id=%s not found", id));
        }
    }

    @PutMapping
    public void editTask(@Valid @RequestBody Task task) {
        try {
            taskService.editTask(task);
        } catch (TaskNotFoundException e) {
            throw TASK_NOT_FOUND.exception(format("Task with id=%s found", task.getId()));
        }
    }
}

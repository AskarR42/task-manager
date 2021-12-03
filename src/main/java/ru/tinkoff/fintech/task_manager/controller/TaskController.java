package ru.tinkoff.fintech.task_manager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.tinkoff.fintech.task_manager.dto.TaskDto;
import ru.tinkoff.fintech.task_manager.service.TaskService;

import java.util.Map;
import java.util.UUID;

@RequestMapping("/task")
@RestController
public class TaskController {

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public Map<String, String> addTask(@RequestBody TaskDto taskDto) {
        UUID id = UUID.randomUUID();
        taskService.save(taskDto, id);
        return Map.of("id", id.toString());
    }

    @PutMapping
    public void editTask(@RequestBody TaskDto taskDto) {
        taskService.editTask(taskDto);
    }

    @DeleteMapping
    public void deleteTask(@RequestParam UUID id) {
        taskService.delete(id);
    }
}

package ru.tinkoff.fintech.task_manager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.tinkoff.fintech.task_manager.dto.LittleTaskDto;
import ru.tinkoff.fintech.task_manager.service.LittleTaskService;

import javax.validation.Valid;
import java.util.Map;
import java.util.UUID;

@RequestMapping("/api/little_task")
@RestController
public class LittleTaskController {

    LittleTaskService littleTaskService;

    @Autowired
    public LittleTaskController(LittleTaskService littleTaskService) {
        this.littleTaskService = littleTaskService;
    }

    @PostMapping
    public Map<String, UUID> addLittleTask(@Valid @RequestBody LittleTaskDto littleTaskDto) {
        UUID littleTaskId = UUID.randomUUID();
        littleTaskService.save(littleTaskDto, littleTaskId);
        return Map.of("id", littleTaskId);
    }

    @PutMapping
    public void editLittleTask(@Valid @RequestBody LittleTaskDto littleTaskDto) {
        littleTaskService.edit(littleTaskDto);
    }

    @DeleteMapping
    public void deleteLittleTask(@RequestParam UUID id) {
        littleTaskService.delete(id);
    }
}

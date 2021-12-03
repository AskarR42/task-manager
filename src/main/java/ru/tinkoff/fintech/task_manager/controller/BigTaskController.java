package ru.tinkoff.fintech.task_manager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.tinkoff.fintech.task_manager.dto.BigTaskDto;
import ru.tinkoff.fintech.task_manager.dto.LittleTaskDto;
import ru.tinkoff.fintech.task_manager.service.BigTaskService;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RequestMapping("/big_task")
@RestController
public class BigTaskController {

    BigTaskService bigTaskService;

    @Autowired
    public BigTaskController(BigTaskService bigTaskService) {
        this.bigTaskService = bigTaskService;
    }

    @PostMapping
    public Map<String, UUID> addBigTask(@Valid @RequestBody BigTaskDto bigTaskDto) {
        UUID bigTaskId = UUID.randomUUID();
        bigTaskService.save(bigTaskDto, bigTaskId);
        return Map.of("id",  bigTaskId);
    }

    @PutMapping
    public List<LittleTaskDto> editBigTask(@Valid @RequestBody BigTaskDto bigTaskDto) {
        bigTaskService.edit(bigTaskDto);
        return bigTaskService.findLittleTasks(bigTaskDto.getId());
    }

    @DeleteMapping
    public void deleteBigTask(@RequestParam UUID id) {
        bigTaskService.delete(id);
    }
}

package ru.tinkoff.fintech.task_manager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.tinkoff.fintech.task_manager.dao.LittleTaskRepository;
import ru.tinkoff.fintech.task_manager.dto.LittleTaskDto;
import ru.tinkoff.fintech.task_manager.exception.LittleTaskNotFoundException;
import ru.tinkoff.fintech.task_manager.model.LittleTask;

import java.util.UUID;

@Service
public class LittleTaskService {

    LittleTaskRepository littleTaskRepository;

    @Autowired
    public LittleTaskService(LittleTaskRepository littleTaskRepository) {
        this.littleTaskRepository = littleTaskRepository;
    }

    public void save(LittleTaskDto littleTaskDto, UUID littleTaskId) {
        littleTaskRepository.save(new LittleTask(littleTaskId, littleTaskDto.getStatus(), littleTaskDto.getText(), littleTaskDto.getBigTaskId()));
    }

    public LittleTaskDto findById(UUID id) {
        LittleTask littleTask = littleTaskRepository.findById(id)
            .orElseThrow(LittleTaskNotFoundException::new);
        return new LittleTaskDto(littleTask.getId(), littleTask.getStatus(), littleTask.getText(), littleTask.getBigTaskId());
    }

    public void delete(UUID id) {
        LittleTask littleTask = littleTaskRepository.findById(id)
            .orElseThrow(LittleTaskNotFoundException::new);
        littleTaskRepository.delete(littleTask);
    }

    public void edit(LittleTaskDto littleTaskDto) {
        if (littleTaskRepository.findById(littleTaskDto.getId())
            .isEmpty()) {
            throw new LittleTaskNotFoundException();
        }
        littleTaskRepository.edit(new LittleTask(littleTaskDto.getId(), littleTaskDto.getStatus(), littleTaskDto.getText(), littleTaskDto.getBigTaskId()));
    }
}

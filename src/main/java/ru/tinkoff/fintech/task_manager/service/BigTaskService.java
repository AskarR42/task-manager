package ru.tinkoff.fintech.task_manager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.tinkoff.fintech.task_manager.dao.BigTaskRepository;
import ru.tinkoff.fintech.task_manager.dao.LittleTaskRepository;
import ru.tinkoff.fintech.task_manager.dto.BigTaskDto;
import ru.tinkoff.fintech.task_manager.dto.LittleTaskDto;
import ru.tinkoff.fintech.task_manager.dto.TaskInfo;
import ru.tinkoff.fintech.task_manager.exception.BigTaskNotFoundException;
import ru.tinkoff.fintech.task_manager.model.BigTask;
import ru.tinkoff.fintech.task_manager.model.LittleTask;

import java.util.List;
import java.util.UUID;

@Service
public class BigTaskService {

    BigTaskRepository bigTaskRepository;
    LittleTaskRepository littleTaskRepository;

    @Autowired
    public BigTaskService(BigTaskRepository bigTaskRepository, LittleTaskRepository littleTaskRepository) {
        this.bigTaskRepository = bigTaskRepository;
        this.littleTaskRepository = littleTaskRepository;
    }

    // Big task always has littleTasks = []
    public void save(BigTaskDto bigTaskDto, UUID bigTaskId) {
        bigTaskRepository.save(new BigTask(bigTaskId, bigTaskDto.getInfo()
            .getName(), bigTaskDto.getInfo()
            .getDescription(), bigTaskDto.getInfo()
            .getDate(), bigTaskDto.getInfo()
            .getColor(), bigTaskDto.getColumnId(), bigTaskDto.getProjectId()));
    }

    public List<LittleTaskDto> findLittleTasks(UUID bigTaskId) {
        BigTask bigTask = bigTaskRepository.findById(bigTaskId)
            .orElseThrow(BigTaskNotFoundException::new);

        return bigTaskRepository.findLittleTasks(bigTask)
            .stream()
            .map(littleTask -> new LittleTaskDto(littleTask.getId(), littleTask.getStatus(), littleTask.getText(), littleTask.getBigTaskId()))
            .toList();
    }

    public BigTaskDto findById(UUID id) {
        BigTask bigTask = bigTaskRepository.findById(id)
            .orElseThrow(BigTaskNotFoundException::new);
        List<LittleTaskDto> littleTasks = findLittleTasks(bigTask.getId());

        return new BigTaskDto(bigTask.getId(), new TaskInfo(bigTask.getName(), bigTask.getDescription(), bigTask.getDate(), bigTask.getColor(), littleTasks), bigTask.getProjectId(), bigTask.getColumnId());
    }

    public void delete(UUID id) {
        BigTask bigTask = bigTaskRepository.findById(id)
            .orElseThrow(BigTaskNotFoundException::new);
        for (LittleTask littleTask : bigTaskRepository.findLittleTasks(bigTask)) {
            littleTaskRepository.delete(littleTask);
        }
        bigTaskRepository.delete(bigTask);
    }

    public void edit(BigTaskDto bigTaskDto) {
        if (bigTaskRepository.findById(bigTaskDto.getId())
            .isEmpty()) {
            throw new BigTaskNotFoundException();
        }
        for (LittleTask littleTask : bigTaskRepository.findLittleTasks(new BigTask(bigTaskDto.getId(), bigTaskDto.toString(), bigTaskDto.getInfo().getDescription(), bigTaskDto.getInfo().getDate(), bigTaskDto.getInfo().getColor(), bigTaskDto.getColumnId(), bigTaskDto.getProjectId()))) {
            littleTaskRepository.delete(littleTask);
        }

        for (LittleTaskDto littleTaskDto : bigTaskDto.getInfo().getSubTasks()) {
            littleTaskRepository.save(new LittleTask(UUID.randomUUID(), littleTaskDto.getStatus(), littleTaskDto.getText(), littleTaskDto.getBigTaskId()));
        }

        bigTaskRepository.edit(new BigTask(
            bigTaskDto.getId(),
            bigTaskDto.getInfo().getName(),
            bigTaskDto.getInfo().getDescription(),
            bigTaskDto.getInfo().getDate(),
            bigTaskDto.getInfo().getColor(),
            bigTaskDto.getColumnId(),
            bigTaskDto.getProjectId()));
    }

}

package ru.tinkoff.fintech.task_manager.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.tinkoff.fintech.task_manager.exception.ApplicationError;
import ru.tinkoff.fintech.task_manager.model.Task;

import java.util.UUID;

import static java.lang.String.format;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.tinkoff.fintech.task_manager.exception.ApplicationError.TASK_NOT_FOUND;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper jackson = new ObjectMapper();

    @Test
    @WithMockUser
    void testAddNewTaskTest() throws Exception {
        Task task = prepareValidTask(UUID.randomUUID());
        String taskJson = jackson.writeValueAsString(task);

        mockMvc.perform(post("/task")
                    .contentType("application/json")
                    .content(taskJson))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void testBlankText() throws Exception {
        Task task = prepareTaskWithBlankText(UUID.randomUUID());
        String taskJson = jackson.writeValueAsString(task);
        String notValidTextMessage = prepareNotValidTextMessage();

        mockMvc.perform(post("/task")
                    .contentType("application/json")
                    .content(taskJson))
                .andExpect(content().string(notValidTextMessage))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser
    void testNullText() throws Exception {
        Task task = prepareTaskWithNullText(UUID.randomUUID());
        String taskJson = jackson.writeValueAsString(task);
        String notValidMessage = prepareNotValidTextMessage();

        mockMvc.perform(post("/task")
                    .contentType("application/json")
                    .content(taskJson))
                .andExpect(content().string(notValidMessage))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser
    void testDeleteSuccess() throws Exception {
        Task task = prepareValidTask(UUID.randomUUID());
        String taskJson = jackson.writeValueAsString(task);

        mockMvc.perform(post("/task")
                    .contentType("application/json")
                    .content(taskJson))
                .andExpect(status().isOk());

        mockMvc.perform(delete("/task")
                    .param("id", task.getId().toString()))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void testDeleteFail() throws Exception {
        UUID id = UUID.randomUUID();
        String notFoundException = jackson.writeValueAsString(prepareTaskNotFoundExceptionCompanion(id));

        mockMvc.perform(delete("/task")
                    .param("id", id.toString()))
                .andExpect(content().string(notFoundException))
                .andExpect(status().is(TASK_NOT_FOUND.code));
    }

    @Test
    @WithMockUser
    void testGetTaskSuccess() throws Exception {
        Task task = prepareValidTask(UUID.randomUUID());
        String taskJson = jackson.writeValueAsString(task);

        mockMvc.perform(post("/task")
                    .contentType("application/json")
                    .content(taskJson))
                .andExpect(status().isOk());

        mockMvc.perform(get("/task")
                    .param("id", task.getId().toString()))
                .andExpect(content().string(taskJson))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void testGetNotExistingTask() throws Exception {
        UUID id = UUID.randomUUID();
        String notFoundException = jackson.writeValueAsString(prepareTaskNotFoundExceptionCompanion(id));

        mockMvc.perform(get("/task")
                    .param("id", id.toString()))
                .andExpect(content().string(notFoundException))
                .andExpect(status().is(TASK_NOT_FOUND.code));
    }

    @Test
    @WithMockUser
    void testEditTaskSuccess() throws Exception {
        Task oldTask = prepareValidTask(UUID.randomUUID());
        String oldTaskJson = jackson.writeValueAsString(oldTask);
        Task newTask = new Task(oldTask.getId(), "newText", 0, UUID.randomUUID());
        String newTaskJson = jackson.writeValueAsString(newTask);

        mockMvc.perform(post("/task")
                    .contentType("application/json")
                    .content(oldTaskJson))
                .andExpect(status().isOk());

        mockMvc.perform(put("/task")
                    .contentType("application/json")
                    .content(newTaskJson))
                .andExpect(status().isOk());
    }

    private Task prepareValidTask(UUID id) {
        return new Task(id, "text", 0, UUID.randomUUID());
    }

    private Task prepareTaskWithBlankText(UUID id) {
        return new Task(id, "", 0, UUID.randomUUID());
    }

    private Task prepareTaskWithNullText(UUID id) {
        return new Task(id, null, 0, UUID.randomUUID());
    }

    private String prepareNotValidTextMessage() {
        return "{\"message\":\"Text cannot be empty or null\"}";
    }

    private ApplicationError.ApplicationExceptionCompanion prepareTaskNotFoundExceptionCompanion(UUID id) {
        return TASK_NOT_FOUND.exception(format("Task with id=%s not found", id.toString())).companion;
    }
}

package ru.tinkoff.fintech.task_manager.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import ru.tinkoff.fintech.task_manager.exception.ApplicationError;
import ru.tinkoff.fintech.task_manager.model.User;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.tinkoff.fintech.task_manager.exception.ApplicationError.USER_NOT_FOUND;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class UserControllerTest {

	@Autowired
	private MockMvc mockMvc;

	private final ObjectMapper jackson = new ObjectMapper();

	@Test
	void testAddNewUserSuccess() throws Exception {
		User user = prepareValidUser(UUID.randomUUID());
		String userJson = jackson.writeValueAsString(user);

		mockMvc.perform(post("/user")
					.contentType("application/json")
					.content(userJson))
				.andExpect(status().isOk());
	}

	@Test
	void testBlankName() throws Exception {
		User user =  prepareUserWithBlankName(UUID.randomUUID());
		String userJson = jackson.writeValueAsString(user);
		String notValidNameMessage = prepareNotValidNameMessage();

		mockMvc.perform(post("/user")
					.contentType("application/json")
					.content(userJson))
				.andExpect(content().string(notValidNameMessage))
				.andExpect(status().isBadRequest());
	}

	@Test
	void testNullName() throws Exception {
		User user = prepareUserWithNullName(UUID.randomUUID());
		String userJson = jackson.writeValueAsString(user);
		String notValidNameMessage = prepareNotValidNameMessage();


		mockMvc.perform(post("/user")
					.contentType("application/json")
					.content(userJson))
				.andExpect(content().string(notValidNameMessage))
				.andExpect(status().isBadRequest());
	}

	@Test
	void testBlankEmail() throws Exception {
		User user = prepareUserWithBlackEmail(UUID.randomUUID());
		String userJson = jackson.writeValueAsString(user);
		String notValidEmailMessage = prepareNotValidEmailMessage();

		mockMvc.perform(post("/user")
					.contentType("application/json")
					.content(userJson))
				.andExpect(content().string(notValidEmailMessage))
				.andExpect(status().isBadRequest());
	}

	@Test
	void testNullEmail() throws Exception {
		User user = prepareUserWithNullEmail(UUID.randomUUID());
		String userJson = jackson.writeValueAsString(user);
		String notValidEmailMessage = prepareNotValidEmailMessage();

		mockMvc.perform(post("/user")
					.contentType("application/json")
					.content(userJson))
				.andExpect(content().string(notValidEmailMessage))
				.andExpect(status().isBadRequest());
	}

	@Test
	void testInvalidEmail() throws Exception {
		User user = prepareUserWithInvalidEmail(UUID.randomUUID());
		String userJson = jackson.writeValueAsString(user);
		String notValidEmailMessage = prepareNotValidEmailMessage();


		mockMvc.perform(post("/user")
					.contentType("application/json")
					.content(userJson))
				.andExpect(content().string(notValidEmailMessage))
				.andExpect(status().isBadRequest());
	}

	@Test
	void testDeleteSuccess() throws Exception {
		User user = prepareValidUser(UUID.randomUUID());
		String userJson = jackson.writeValueAsString(user);

		mockMvc.perform(post("/user")
					.contentType("application/json")
					.content(userJson))
				.andExpect(status().isOk());

		mockMvc.perform(delete("/user")
					.param("id", user.getId().toString()))
				.andExpect(status().isOk());
	}

	@Test
	void testDeleteFail() throws Exception {
		UUID randomId = UUID.randomUUID();
		String notFoundException = jackson.writeValueAsString(prepareUserNotFoundExceptionCompanion(randomId));

		mockMvc.perform(delete("/user")
					.param("id", randomId.toString()))
				.andExpect(content().string(notFoundException))
				.andExpect(status().is(USER_NOT_FOUND.code));
	}

	@Test
	void testGetUserSuccess() throws Exception {
		User user = prepareValidUser(UUID.randomUUID());
		String userJson = jackson.writeValueAsString(user);

		mockMvc.perform(post("/user")
					.contentType("application/json")
					.content(userJson))
				.andExpect(status().isOk());

		mockMvc.perform(get("/user")
					.param("id", user.getId().toString()))
				.andExpect(content().string(userJson))
				.andExpect(status().isOk());
	}

	@Test
	void testGetNotExistingUser() throws Exception {
		UUID randomId = UUID.randomUUID();
		String notFoundException = jackson.writeValueAsString(prepareUserNotFoundExceptionCompanion(randomId));

		mockMvc.perform(get("/user")
					.param("id", randomId.toString()))
				.andExpect(content().string(notFoundException))
				.andExpect(status().is(USER_NOT_FOUND.code));
	}

	@Test
	void testEditUserSuccess() throws Exception {
		User oldUser = prepareValidUser(UUID.randomUUID());
		String oldUserJson = jackson.writeValueAsString(oldUser);
		User newUser = new User(oldUser.getId(), "newName", "newEmail@email.com", "newPassword");
		String newUserJson = jackson.writeValueAsString(newUser);

		mockMvc.perform(post("/user")
					.contentType("application/json")
					.content(oldUserJson))
				.andExpect(status().isOk());

		mockMvc.perform(put("/user")
					.contentType("application/json")
					.content(newUserJson))
				.andExpect(status().isOk());
	}

	private User prepareValidUser(UUID id) {
		return new User(id, "testName", "testEmail@email.com", "testPassword");
	}

	private User prepareUserWithBlankName(UUID id) {
		return new User(id, "", "testEmail@email.com", "testPassword");
	}

	private User prepareUserWithNullName(UUID id) {
		return new User(id, null, "testEmail@email.com", "testPassword");
	}

	private User prepareUserWithBlackEmail(UUID id) {
		return new User(id, "testName", "", "testPassword");
	}

	private User prepareUserWithNullEmail(UUID id) {
		return new User(id, "testName", null, "testPassword");
	}

	private User prepareUserWithInvalidEmail(UUID id) {
		return new User(id, "testName", "invalidEmail.com", "testPassword");
	}

	private ApplicationError.ApplicationExceptionCompanion prepareUserNotFoundExceptionCompanion(UUID id) {
		return USER_NOT_FOUND.exception(String.format("User with id=%s not found", id.toString())).companion;
	}

	private String prepareNotValidNameMessage() {
		return "{\"message\":\"Name cannot be empty or null\"}";
	}

	private String prepareNotValidEmailMessage() {
		return "{\"message\":\"Invalid email\"}";
	}

}

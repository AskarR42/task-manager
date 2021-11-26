package ru.tinkoff.fintech.task_manager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.tinkoff.fintech.task_manager.exception.UserAlreadyExistsException;
import ru.tinkoff.fintech.task_manager.exception.UserNotFoundException;
import ru.tinkoff.fintech.task_manager.model.User;
import ru.tinkoff.fintech.task_manager.service.UserService;

import javax.validation.Valid;
import java.util.UUID;

import static java.lang.String.format;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static ru.tinkoff.fintech.task_manager.exception.ApplicationError.USER_ALREADY_EXISTS;
import static ru.tinkoff.fintech.task_manager.exception.ApplicationError.USER_NOT_FOUND;

@RequestMapping("/user")
@RestController()
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping (consumes = APPLICATION_JSON_VALUE)
    public void addUser(@Valid @RequestBody User user) {
        try {
            userService.insertNewUser(user);
        } catch (UserAlreadyExistsException e) {
            throw USER_ALREADY_EXISTS.exception(format("User with id=%s already exists", user.getId()));
        }
    }

    @DeleteMapping
    public void deleteUser(@RequestParam UUID id) {
        try {
            userService.deleteUserById(id);
        } catch (UserNotFoundException e) {
            throw USER_NOT_FOUND.exception(format("User with id=%s not found", id));
        }
    }

    @GetMapping
    public User findUser(@RequestParam UUID id) {
        try {
            return userService.getUserById(id);
        } catch (UserNotFoundException e) {
            throw USER_NOT_FOUND.exception(format("User with id=%s not found", id));
        }
    }

    @PutMapping(consumes = APPLICATION_JSON_VALUE)
    public void editUser(@Valid @RequestBody User user) {
        try {
            userService.editUser(user);
        } catch (UserNotFoundException e) {
            throw USER_NOT_FOUND.exception(format("User with id=%s not found", user.getId()));
        }
    }
}

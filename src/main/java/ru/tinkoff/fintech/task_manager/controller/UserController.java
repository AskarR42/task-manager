package ru.tinkoff.fintech.task_manager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.tinkoff.fintech.task_manager.dto.UserDto;
import ru.tinkoff.fintech.task_manager.service.UserService;

import javax.validation.Valid;
import java.util.UUID;

@RequestMapping("/api/user")
@RestController()
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public void addUser(@Valid @RequestBody UserDto userDto) {
        userService.save(userDto);
    }

    @GetMapping
    public UserDto findCurrentUser(Authentication authentication) {
        return userService.findCurrentUser(authentication);
    }

    @PutMapping
    public void editUser(@Valid @RequestBody UserDto userDto) {
        userService.edit(userDto);
    }

    @DeleteMapping
    public void deleteUser(@RequestParam UUID id) {
        userService.delete(id);
    }
}

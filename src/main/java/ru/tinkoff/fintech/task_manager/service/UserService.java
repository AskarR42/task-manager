package ru.tinkoff.fintech.task_manager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import ru.tinkoff.fintech.task_manager.dao.UserRepository;
import ru.tinkoff.fintech.task_manager.dto.UserDto;
import ru.tinkoff.fintech.task_manager.model.User;

import java.util.List;
import java.util.UUID;

import static java.lang.String.format;
import static ru.tinkoff.fintech.task_manager.exception.ApplicationError.USER_ALREADY_EXISTS;
import static ru.tinkoff.fintech.task_manager.exception.ApplicationError.USER_NOT_FOUND;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void save(UserDto userDto) {
        if (userRepository.findByName(userDto.getName()).isPresent()) {
            throw USER_ALREADY_EXISTS.exception(format("User with name=%s already exists", userDto.getName()));
        }
        User user = new User(UUID.randomUUID(), userDto.getName(), userDto.getNickname(), userDto.getEmail(), userDto.getPassword());
        userRepository.save(user);
    }

    public UserDto findById(UUID id) {
        if (userRepository.findById(id).isPresent()) {
            User user = userRepository.findById(id).get();
            return new UserDto(user.getId(), user.getName(), user.getNickname(), user.getEmail(), user.getPassword());
        } else {
            throw USER_NOT_FOUND.exception(format("User with id=%s not found", id));
        }
    }

    public UserDto findCurrentUser(Authentication authentication) {
        if (userRepository.findByName(authentication.getName()).isPresent()) {
            User user = userRepository.findByName(authentication.getName()).get();
            return new UserDto(user.getId(), user.getName(), user.getNickname(), user.getEmail(), user.getPassword());
        } else {
            throw USER_NOT_FOUND.exception("Current user not found");
        }
    }

    public List<UserDto> findAll() {
        return userRepository.findAll().stream().map(user -> new UserDto(user.getId(), user.getName(), user.getNickname(), user.getEmail(), user.getPassword())).toList();
    }

    public void edit(UserDto userDto) {
        if (userRepository.findById(userDto.getId()).isPresent()) {
            userRepository.edit(new User(userDto.getId(), userDto.getName(), userDto.getNickname(), userDto.getEmail(), userDto.getPassword()));
        } else {
            throw USER_NOT_FOUND.exception(format("User with id=%s not found", userDto.getId()));
        }
    }

    public void delete(UUID id) {
        if (userRepository.findById(id).isPresent()) {
            userRepository.delete(userRepository.findById(id).get());
        } else {
            throw USER_NOT_FOUND.exception(format("User with id=%s not found", id));
        }
    }
}

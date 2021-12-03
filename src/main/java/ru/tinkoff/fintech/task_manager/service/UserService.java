package ru.tinkoff.fintech.task_manager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import ru.tinkoff.fintech.task_manager.dao.UserRepository;
import ru.tinkoff.fintech.task_manager.exception.UserNotFoundException;
import ru.tinkoff.fintech.task_manager.model.User;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void save(User user) {
        user.setId(UUID.randomUUID());
        userRepository.save(user);
    }

    public User findById(UUID id) {
        return userRepository.findById(id).orElseThrow(UserNotFoundException::new);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public void delete(UUID id) {
        User user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        userRepository.delete(user);
    }

    public void edit(User user) {
        if (userRepository.findById(user.getId()).isEmpty()) {
            throw new UserNotFoundException();
        }
        userRepository.edit(user);
    }

    public UUID findCurrentUserId(Authentication authentication) {
        User user = userRepository.findByName(authentication.getName()).orElseThrow(UserNotFoundException::new);
        return user.getId();
    }
}

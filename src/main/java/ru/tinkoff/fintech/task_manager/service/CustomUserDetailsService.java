package ru.tinkoff.fintech.task_manager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.tinkoff.fintech.task_manager.dao.UserRepository;
import ru.tinkoff.fintech.task_manager.model.User;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository) {
         this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = userRepository.findByName(userName).orElseThrow();
        return org.springframework.security.core.userdetails.User
                .builder()
                .username(user.getName())
                .password("{noop}" + user.getPassword())
                .roles("USER")
                .build();

    }
}

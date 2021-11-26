package ru.tinkoff.fintech.task_manager.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.tinkoff.fintech.task_manager.validation.ValidEmail;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@AllArgsConstructor
public class User {

    @NotNull(message = "Id cannot be null")
    private UUID id;

    @NotEmpty(message = "Name cannot be empty or null")
    private String name;

    @ValidEmail
    private String email;

    @NotEmpty(message = "Password cannot be empty or null")
    private String password;

    public static User of(String name, String email, String password) {
        return new User(UUID.randomUUID(), name, email, password);
    }
}

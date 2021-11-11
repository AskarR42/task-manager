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

    @NotNull(message = "Name cannot be null")
    @NotEmpty(message = "Name cannot be empty")
    private String name;

    @ValidEmail
    private String email;

    @NotNull
    @NotEmpty
    private String password;

    public static User of(String name, String email, String password) {
        return new User(UUID.randomUUID(), name, email, password);
    }
}

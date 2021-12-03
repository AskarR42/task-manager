package ru.tinkoff.fintech.task_manager.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.tinkoff.fintech.task_manager.validation.ValidEmail;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private UUID id;

    @NotEmpty(message = "Name cannot be empty or null")
    private String name;

    @NotEmpty(message = "Nickname cannot be empty or null")
    private String nickname;

    @ValidEmail
    private String email;

    @NotEmpty(message = "Password cannot be empty or null")
    private String password;
}

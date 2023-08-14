package ru.yandex.practicum.model;

import lombok.Data;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
public class User {
    private Long id;

    @NotNull
    @NotBlank
    @Email
    private String email;

    @NotNull
    @NotBlank
    private String login;

    private String name;
    @Past
    private LocalDate birthday;

    private Set<Long> friends = new HashSet<>();

    public User(Long id, String email, String login, String name, LocalDate birthday) {
        this.id = id;
        this.email = email;
        this.login = login;
        this.name = (name == null || name.isEmpty() || name.isBlank()) ? login : name;
        this.birthday = birthday;
    }

}

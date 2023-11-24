package ru.yandex.practicum.model;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.Set;

@Data
@Builder
public class User {
    private Integer id;

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

    private Set<Integer> friends;


}

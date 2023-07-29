package ru.yandex.practicum.model;

import lombok.Data;
import ru.yandex.practicum.anatation.ValidName;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import java.time.LocalDate;

@Data
public class User {
    private int id;
    @Email
    private String email;
    @NotBlank
    private String login;
    @ValidName(message = "имя для отображения не может быть пустым")
    private String name;
    @Past
    private LocalDate birthday;
}

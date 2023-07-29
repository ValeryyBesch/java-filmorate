package ru.yandex.practicum.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;


import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
public class User {
    private int id;
    @Email
    @NotEmpty
    private String email;
    @NotBlank
    private String login;
    // так и не понял как через свою аннотацию установить логин если поле пустое
    private String name;
    @Past
    private LocalDate birthday;
}

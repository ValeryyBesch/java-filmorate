package ru.yandex.practicum.model;

import javax.validation.constraints.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.yandex.practicum.annotation.ValidReleaseDate;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Film {
    private Long id;
    @NotBlank
    @NotNull
    private String name;
    @NotNull
    @Size(max = 200)
    private String description;
    @NotNull
    @Past
    @ValidReleaseDate
    private LocalDate releaseDate;
    @NotNull
    @Positive
    private int duration;
    private Set<Long> likes;
}

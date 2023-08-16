package ru.yandex.practicum.controler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.model.Film;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FilmControllerTest {
    private Film film;
    private static Validator validator;
    private Set<ConstraintViolation<Film>> violations;

    @BeforeEach
    public void beforeEach() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
        film = new Film(1L, "name", "desr", LocalDate.of(2007, 5, 19),
                2, Collections.singleton(1L));
        violations = validator.validate(film);
    }

    @Test
    public void testAddFilm() {
        violations = validator.validate(film);
        assertEquals(0, violations.size(), "Ошибки валидации");
        List<Film> films = new ArrayList<>();
        films.add(film);
        assertTrue(films.contains(film), "Фильм не был добавлен в список");
    }

    @Test
    public void testEmptyFilmName() {
        film.setName("");
        violations = validator.validate(film);
        assertEquals(1, violations.size(), "Ошибки валидации");
    }

}
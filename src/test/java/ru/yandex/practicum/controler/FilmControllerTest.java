package ru.yandex.practicum.controler;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.model.Film;
import ru.yandex.practicum.service.FilmService;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class FilmControllerTest {
    FilmService filmService;
    private final FilmController filmController = new FilmController(filmService);
    Film film = Film.builder()
            .id(1l)
            .name("nameFilm")
            .duration(100)
            .releaseDate(LocalDate.of(2000, 10, 10))
            .description("description")
            .build();

    @Test
    void addFilm() {
        filmController.addFilm(film);
        assertNotNull(filmController.getAllFilms());
    }

    @Test
    void updateUser() {
        Film film1 = Film.builder()
                .id(1l)
                .name("name")
                .duration(100)
                .releaseDate(LocalDate.of(2000, 10, 10))
                .description("description")
                .build();
        filmController.addFilm(film);
        filmController.updateUser(film1);
        assertEquals(filmController.getAllFilms().size(), 1);
    }
}
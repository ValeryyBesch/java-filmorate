package ru.yandex.practicum.controler;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.model.Film;
import ru.yandex.practicum.service.FilmService;
import ru.yandex.practicum.storage.film.FilmStorage;
import ru.yandex.practicum.storage.film.InMemoryFilmStorage;
import ru.yandex.practicum.storage.user.InMemoryUserStorage;
import ru.yandex.practicum.storage.user.UserStorage;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class FilmControllerTest {
    FilmStorage filmStorage = new InMemoryFilmStorage();
    UserStorage userStorage = new InMemoryUserStorage();
    FilmService filmService = new FilmService(filmStorage,userStorage);
    Film film = Film.builder()
            .id(1L)
            .name("nameFilm")
            .duration(100)
            .releaseDate(LocalDate.of(2000, 10, 10))
            .description("description")
            .build();

    @Test
    void addFilm() {
        filmService.create(film);
        assertNotNull(filmService.getAllFilms());
    }

    @Test
    void updateUser() {
        Film film1 = Film.builder()
                .id(1L)
                .name("name")
                .duration(100)
                .releaseDate(LocalDate.of(2000, 10, 10))
                .description("description")
                .build();
        filmService.create(film);
        filmService.update(film1);
        assertEquals(filmService.getAllFilms().size(), 1);
    }
}
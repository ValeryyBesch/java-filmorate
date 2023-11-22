package ru.yandex.practicum.storage.film;

import ru.yandex.practicum.model.Film;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

public interface FilmStorage {
    List<Film> findAllFilms();

    Film addFilm(Film film);

    Film updateFilm(Film film);

    Film getFilmById(int id);

    Film like(int filmId, int userId);

    Film deleteLike(int filmId, int userId);

    List<Film> getRating(int count);
}

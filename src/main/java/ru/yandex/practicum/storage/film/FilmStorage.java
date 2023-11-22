package ru.yandex.practicum.storage.film;

import ru.yandex.practicum.model.Film;

import javax.validation.Valid;
import java.util.List;

public interface FilmStorage {

    List<Film> getAllFilms();

    Film create(@Valid Film film);

    Film update(@Valid Film film);

    Film getFilm(Long filmId);

    Film delete(Long filmId);
}

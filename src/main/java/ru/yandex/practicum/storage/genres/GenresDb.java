package ru.yandex.practicum.storage.genres;

import ru.yandex.practicum.model.Film;
import ru.yandex.practicum.model.Genre;

import java.util.List;
import java.util.Set;

public interface GenresDb {

    List<Genre> findAll();

    Set<Genre> getGenres(int id);

    void addGenres(Film film);

    void updateGenres(Film film);

    Genre getGenre(int id);

    void addGenreByFilm(Film film);
}

package ru.yandex.practicum.storage.film;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import ru.yandex.practicum.model.Film;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class InMemoryFilmStorage implements FilmStorage {
    private final Map<Long, Film> films = new HashMap<>();
    private Long generateId = 1L;

    @Override
    public List<Film> getAllFilms() {
        log.debug("получаем все фильмы");
        return new ArrayList<>(films.values());
    }

    @Override
    public Film create(Film film) {
        log.debug("добавляем фильм: {}", film);
        film.setId(generateId++);
        films.put(film.getId(), film);
        return film;
    }

    @Override
    public Film update(Film film) {
        log.debug("обновляем фильм: {}", film);
        if (films.containsKey(film.getId())) {
            films.put(film.getId(), film);
        } else {
            log.debug("фильм с {} id не найден", film.getId());
            throw new RuntimeException("фильм не найден");
        }
        return film;
    }

    @Override
    public Film getFilm(Long filmId) {
        log.debug("получение фильма по id {}", filmId);
        if (!films.containsKey(filmId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "фильм с id: " + filmId + " не найден!");
        }
        return films.get(filmId);
    }

    @Override
    public Film delete(Long filmId) {
        log.debug("удаление фильма по id {}", filmId);
        if (!films.containsKey(filmId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "фильм с id: " + filmId + " не найден!");
        }
        return films.remove(filmId);
    }
}

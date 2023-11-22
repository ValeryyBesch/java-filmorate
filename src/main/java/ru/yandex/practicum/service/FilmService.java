package ru.yandex.practicum.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.yandex.practicum.model.Film;
import ru.yandex.practicum.model.User;
import ru.yandex.practicum.storage.film.FilmStorage;
import ru.yandex.practicum.storage.user.UserStorage;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class FilmService {
    private final FilmStorage filmStorage;
    private final UserStorage userStorage;

    public FilmService(FilmStorage filmStorage, UserStorage userStorage) {
        this.filmStorage = filmStorage;
        this.userStorage = userStorage;
    }

    public List<Film> getAllFilms() {
        return filmStorage.getAllFilms();
    }

    public Film create(Film film) {
        return filmStorage.create(film);
    }

    public Film update(Film film) {
        return filmStorage.update(film);
    }

    public Film getFilmById(Long filmId) {
        return filmStorage.getFilm(filmId);
    }

    public Film delete(Long filmId) {
        log.debug("удаляем фильм id:{}", filmId);
        return filmStorage.delete(filmId);
    }

    public List<Film> getPopular(Integer count) {
        log.info("получаем список фильмов по количеству лайков :{}", count);
        return filmStorage.getAllFilms().stream()
                .sorted((o1, o2) -> o2.getLikes().size() - o1.getLikes().size())
                .limit(count)
                .collect(Collectors.toList());
    }

    public void addLike(Long filmId, Long userId) {
        log.debug("добавляем лайк фильму id:{}", filmId);
        Film film = filmStorage.getFilm(filmId);
        User user = userStorage.getUser(userId);
        film.getLikes().add(user.getId());
    }

    public void deleteLike(Long filmId, Long userId) {
        log.debug("удаляем лайк с фильма id:{}", filmId);
        Film film = filmStorage.getFilm(filmId);
        if (!film.getLikes().remove(userId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Лайк от пользователя c id=" + userId + " не найден!");
        }
    }
}

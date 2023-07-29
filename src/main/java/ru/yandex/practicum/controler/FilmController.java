package ru.yandex.practicum.controler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.model.Film;


import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/film")
@Validated
public class FilmController {
    private Map<Integer, Film> films = new HashMap<>();
    private int generateId = 1;

    @PostMapping
    public Film addFilm(@Valid @RequestBody Film film) {
        log.debug("добавляем фильм: {}", film);
        if (film == null) {
            System.out.println("нет фильма");
            return null;
        } else {
            film.setId(generateId++);
            films.put(film.getId(), film);
            return film;
        }
    }

    @PutMapping("/{id}")
    public Film updateUser(@PathVariable int id, @RequestBody @Valid Film film1) {
        log.debug("обновляем пользователя: {}", film1);
        Film film = films.get(id);

        if (film != null) {
            film.setName(film1.getName());
            film.setDescription(film1.getDescription());
            film.setDuration(film1.getDuration());
            film.setReleaseDate(film1.getReleaseDate());
            film.setId(film1.getId());
            films.put(id, film);
        }
        return film;
    }

    @GetMapping
    public List<Film> getAllFilms() {
        log.debug("получаем все фильмы");
        return new ArrayList<>(films.values());
    }
}

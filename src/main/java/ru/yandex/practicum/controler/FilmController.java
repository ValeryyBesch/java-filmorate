package ru.yandex.practicum.controler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.model.Film;


import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/films")
public class FilmController {
    private final Map<Integer, Film> films = new HashMap<>();
    private int generateId = 1;

    @PostMapping
    public Film addFilm(@RequestBody @Valid Film film) {
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

    @PutMapping
    public Film updateUser(@RequestBody @Valid Film film) {
        log.debug("обновляем фильм: {}", film);
        if (films.containsKey(film.getId())) {
            films.put(film.getId(), film);
        } else {
            throw new RuntimeException("фильм не найден");
        }
        return film;
    }

    @GetMapping
    public List<Film> getAllFilms() {
        log.debug("получаем все фильмы");
        return new ArrayList<>(films.values());
    }
}

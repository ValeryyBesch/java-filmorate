package ru.yandex.practicum.controler;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.model.Film;
import ru.yandex.practicum.service.FilmService;


import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;


@RestController
@Slf4j
@RequestMapping("/films")
@AllArgsConstructor
public class FilmController {
    FilmService filmService;

    @PostMapping
    public Film addFilm(@RequestBody @Valid Film film) {
        return filmService.create(film);
    }

    @PutMapping
    public Film updateUser(@RequestBody @Valid Film film) {
        return filmService.update(film);
    }

    @GetMapping
    public List<Film> getAllFilms() {
        return filmService.getAllFilms();
    }

    @GetMapping("/{id}")
    public Film getFilm(@PathVariable Long id) {
        return filmService.getFilmById(id);
    }

    @PutMapping("/{id}/like/{userId}")
    public void addLike(@PathVariable Long id, @PathVariable Long userId) {
        filmService.addLike(id, userId);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public void deleteLike(@PathVariable Long id, @PathVariable Long userId) {
        filmService.deleteLike(id, userId);
    }

    @GetMapping("/popular")
    public List<Film> getPopular(@RequestParam(name = "count", defaultValue = "10") @Positive Integer count) {
        return filmService.getPopular(count);
    }

    @DeleteMapping("/{id}")
    public Film delete(@PathVariable @NotNull Long id) {
        return filmService.delete(id);
    }
}

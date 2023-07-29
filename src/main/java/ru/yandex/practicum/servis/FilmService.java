package ru.yandex.practicum.servis;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.model.Film;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FilmService {
    private Map<Integer, Film> films = new HashMap<>();
    private int generateId = 1;

    public Film addFilm(Film film) {
        if (film == null) {
            System.out.println("нет фильма");
            return null;
        } else {
            film.setId(generateId++);
            films.put(film.getId(), film);
            return film;
        }
    }

    public Film updateFilm(Film film) {
        films.put(film.getId(), film);
        return film;
    }

    public List<Film> getAllFilms(){
        return new ArrayList<>(films.values());
    }
}

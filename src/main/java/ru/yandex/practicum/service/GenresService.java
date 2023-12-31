package ru.yandex.practicum.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.model.Genre;
import ru.yandex.practicum.storage.genres.GenresDb;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class GenresService {

    private final GenresDb genreDbStorage;

    public Genre getGenre(int genreId) {
        return genreDbStorage.getGenre(genreId);
    }

    public List<Genre> findAll() {
        return genreDbStorage.findAll();
    }
}
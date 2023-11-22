package ru.yandex.practicum.storage.mpa;

import ru.yandex.practicum.model.Film;
import ru.yandex.practicum.model.Mpa;

import java.util.List;

public interface MpaDb {

    List<Mpa> findAll();

    Mpa getMpa(int mpaId);

    void addMpa(Film film);
}

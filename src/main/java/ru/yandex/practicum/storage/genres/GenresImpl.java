package ru.yandex.practicum.storage.genres;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.exceotion.NotFoundException;
import ru.yandex.practicum.model.Film;
import ru.yandex.practicum.model.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Component
@RequiredArgsConstructor
public class GenresImpl implements GenresDb {
    private final JdbcTemplate jdbcTemplate;


    public List<Genre> findAll() {
        List<Genre> genreList = new ArrayList<>();
        SqlRowSet genreRows = jdbcTemplate.queryForRowSet("SELECT genre_id, name FROM genre_type");
        while (genreRows.next()) {
            Genre genre = Genre.builder()
                    .id(genreRows.getInt("genre_id"))
                    .name(genreRows.getString("name"))
                    .build();
            genreList.add(genre);
        }
        return genreList;
    }

    public Set<Genre> getGenres(int id) {
        Set<Genre> genreSet = new LinkedHashSet<>();
        SqlRowSet genreRows = jdbcTemplate.queryForRowSet("SELECT id, film_id, genre_id FROM genre " +
                "ORDER BY genre_id ASC");
        while (genreRows.next()) {
            if (genreRows.getLong("film_id") == id) {
                genreSet.add(getGenre(genreRows.getInt("genre_id")));
            }
        }
        return genreSet;
    }

    public void addGenres(Film film) {
        if (Objects.isNull(film.getGenres())) {
            return;
        }
        film.getGenres().forEach(g -> {
            String sqlQuery = "INSERT INTO genre(film_id, genre_id) VALUES (?, ?)";
            jdbcTemplate.update(sqlQuery,
                    film.getId(),
                    g.getId());
        });
    }

    public void updateGenres(Film film) {
        String sqlQuery = "DELETE FROM genre WHERE film_id = ?";
        jdbcTemplate.update(sqlQuery, film.getId());
        addGenres(film);
    }

    public Genre getGenre(int id) {

        String sqlQuery = "SELECT genre_id, name FROM genre_type WHERE genre_id=?";
        try {
            return jdbcTemplate.queryForObject(sqlQuery, this::mapRowToGenre, id);
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException("жанр не найден.");
        }
    }

    public void addGenreByFilm(Film film) {
        if (Objects.isNull(film.getGenres())) {
            return;
        }
        film.getGenres().forEach(g -> g.setName(getGenre(g.getId()).getName()));
    }


    private Genre mapRowToGenre(ResultSet resultSet, int rowNum) throws SQLException {
        return Genre.builder()
                .id(resultSet.getInt("genre_id"))
                .name(resultSet.getString("name"))
                .build();
    }
}
package ru.yandex.practicum.model;

import lombok.Data;

@Data
public class Like {
    private Integer id;
    private Integer userId;
    private Integer filmId;
}

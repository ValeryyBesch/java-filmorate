package ru.yandex.practicum.storage.like;

import java.util.Set;

public interface LikeDb {
    Set<Integer> getLikes(int id);
}

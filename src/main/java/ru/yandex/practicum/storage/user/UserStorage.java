package ru.yandex.practicum.storage.user;

import ru.yandex.practicum.model.User;

import javax.validation.Valid;
import java.util.List;

public interface UserStorage {
    User createUser(@Valid User user);

    User updateUser(@Valid User user);

    List<User> getAllUsers();

    User getUser(Long userId);

    User delete(Long userId);
}

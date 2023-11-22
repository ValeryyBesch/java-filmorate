package ru.yandex.practicum.storage.user;

import ru.yandex.practicum.model.User;

import java.util.List;

public interface UserStorage {

    List<User> findAllUsers();

    User addUser(User user);

    User updateUser(User user);

    User addFriend(Integer userId, Integer friendId);

    User deleteFriend(Integer userId, Integer friendId);

    List<User> getMutualFriends(Integer id, Integer otherId);

    User getUserById(Integer id);

    List<User> getFriendsByUserId(Integer id);

}


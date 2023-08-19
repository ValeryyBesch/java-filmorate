package ru.yandex.practicum.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.model.User;
import ru.yandex.practicum.storage.user.UserStorage;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserService {
    private final UserStorage userStorage;

    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public User createUser(User user) {
        return userStorage.createUser(user);
    }

    public User updateUser(User user) {
        return userStorage.updateUser(user);
    }

    public List<User> getAllUsers() {
        return userStorage.getAllUsers();
    }

    public User getUser(Long id) {
        return userStorage.getUser(id);
    }

    public User delete(Long id) {
        return userStorage.delete(id);
    }

    public void addFriend(Long userId, Long friendId) {
        log.debug("добавляем друга по id: {}",friendId);
        if (userId.equals(friendId)) {
            log.debug("Попытка добавить себя в друзья.");
            return;
        }
        User user = userStorage.getUser(userId);
        User friend = userStorage.getUser(friendId);
        user.getFriends().add(friendId);
        friend.getFriends().add(userId);
    }

    public void deleteFriend(Long userId, Long friendId) {
        log.debug("удаляем друга по id: {}",friendId);
        User user = userStorage.getUser(userId);
        User friend = userStorage.getUser(friendId);
        user.getFriends().remove(friendId);
        friend.getFriends().remove(userId);
    }

    public List<User> getFriends(Long userId) {
        log.debug("получаем список друзей пользователя по id: {}",userId);
        Set<Long> friendIds = userStorage.getUser(userId).getFriends();
        return friendIds.stream().map(userStorage::getUser).collect(Collectors.toList());
    }

    public List<User> getCommonFriends(Long firstUserId, Long secondUserId) {
        log.debug("получаем список общих друзей по id {} и {}",firstUserId, secondUserId);
        User firstUser = userStorage.getUser(firstUserId);
        User secondUser = userStorage.getUser(secondUserId);
        Set<Long> firstUserFriends = new HashSet<>(firstUser.getFriends());
        firstUserFriends.retainAll(secondUser.getFriends());
        return firstUserFriends.stream()
                .map(userStorage::getUser)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
}

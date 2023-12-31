package ru.yandex.practicum.storage.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.exceotion.NotFoundException;
import ru.yandex.practicum.exceotion.NotValidationException;
import ru.yandex.practicum.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

@Primary
@Slf4j
@Component
public class UserDbStorage implements UserStorage {

    private final JdbcTemplate jdbcTemplate;

    public UserDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<User> findAllUsers() {
        List<User> users = new ArrayList<>();
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet("SELECT * FROM users");
        while (rowSet.next()) {
            User user = User.builder()
                    .id(rowSet.getInt("user_id"))
                    .name(rowSet.getString("name"))
                    .login(rowSet.getString("login"))
                    .email(rowSet.getString("email"))
                    .birthday(Objects.requireNonNull(rowSet.getDate("birthday")).toLocalDate())
                    .build();
            users.add(user);
        }
        return users;
    }

    @Override
    public User addUser(User user) {
        if (userExistsByEmailOrLogin(user.getEmail(), user.getLogin())) {
            throw new NotFoundException("Пользователь с таким email или login уже существует");
        }
        validationUser(user);
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("users")
                .usingGeneratedKeyColumns("user_id");
        user.setId(simpleJdbcInsert.executeAndReturnKey(toMap(user)).intValue());
        log.info("Поступил запрос на добавление пользователя. Пользователь добавлен.");
        return user;
    }

    private boolean userExistsByEmailOrLogin(String email, String login) {
        String sqlQuery = "SELECT COUNT(*) FROM users WHERE email = ? OR login = ?";
        Integer count = jdbcTemplate.queryForObject(sqlQuery, Integer.class, email, login);
        return count != null && count > 0;
    }


    @Override
    public User updateUser(User user) {
        validationUser(user);
        String sqlQuery = "UPDATE users SET " +
                "email=?, login=?, name=?, birthday=? WHERE user_id=?";
        int rowsCount = jdbcTemplate.update(sqlQuery, user.getEmail(), user.getLogin(),
                user.getName(), user.getBirthday(), user.getId());
        if (rowsCount > 0) {
            log.info("Поступил запрос на изменения пользователя. Пользователь изменён.");
            return user;
        }
        throw new NotFoundException("Пользователь не найден.");
    }


    @Override
    public User addFriend(Integer userId, Integer friendId) {
        User user = getUserById(userId);
        if (Objects.equals(userId, friendId)) {
            throw new NotValidationException("Нельзя добавить самого себя");
        }
        try {
            getUserById(friendId);
        } catch (RuntimeException e) {
            throw new NotFoundException("Пользователь не найден.");
        }
        String sqlQuery = "INSERT INTO FRIENDS (user_id, friend_id) VALUES(?, ?)";
        jdbcTemplate.update(sqlQuery, userId, friendId);
        return user;
    }

    @Override
    public User deleteFriend(Integer userId, Integer friendId) {
        User user = getUserById(userId);
        String sqlQuery = "DELETE FROM FRIENDS WHERE user_id = ? AND friend_id = ?";
        jdbcTemplate.update(sqlQuery, userId, friendId);
        return user;
    }

    @Override
    public List<User> getMutualFriends(Integer id, Integer otherId) {
        String sqlQuery = "SELECT u.user_id, u.email, u.login, u.name, u.birthday " +
                "FROM users u " +
                "JOIN friends f1 ON u.user_id = f1.friend_id " +
                "JOIN friends f2 ON u.user_id = f2.friend_id " +
                "WHERE f1.user_id = ? AND f2.user_id = ?";
        return new ArrayList<>(jdbcTemplate.query(sqlQuery, this::mapRowToUser, id, otherId));
    }


    @Override
    public User getUserById(Integer id) {
        String sqlQuery = "SELECT user_id, email, login, name, birthday FROM users WHERE user_id=?";
        try {
            return jdbcTemplate.queryForObject(sqlQuery, this::mapRowToUser, id);
        } catch (RuntimeException e) {
            throw new NotFoundException("Пользователь не найден.");
        }
    }

    @Override
    public List<User> getFriendsByUserId(Integer id) {
        String sqlQuery = "SELECT user_id, email, login, name, birthday FROM users WHERE user_id IN" +
                "(SELECT friend_id FROM friends WHERE user_id=?)";
        return new ArrayList<>(jdbcTemplate.query(sqlQuery, this::mapRowToUser, id));
    }

    private Map<String, Object> toMap(User user) {
        Map<String, Object> values = new HashMap<>();
        values.put("email", user.getEmail());
        values.put("login", user.getLogin());
        values.put("name", user.getName());
        values.put("birthday", user.getBirthday());
        return values;
    }

    private User mapRowToUser(ResultSet resultSet, int rowNum) throws SQLException {
        User user = User.builder()
                .id(resultSet.getInt("user_id"))
                .email(resultSet.getString("email"))
                .login(resultSet.getString("login"))
                .name(resultSet.getString("name"))
                .birthday(resultSet.getDate("birthday").toLocalDate())
                .build();
        user.setFriends(getFriendsByUserId(user.getId()).stream().map(User::getId).collect(Collectors.toSet()));
        return user;
    }

    private void validationUser(User user) throws NotValidationException {
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
    }
}
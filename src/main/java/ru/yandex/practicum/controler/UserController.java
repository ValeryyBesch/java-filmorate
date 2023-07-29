package ru.yandex.practicum.controler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.model.User;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
@Validated
@Slf4j
public class UserController {
    private Map<Integer, User> users = new HashMap<>();


    @PostMapping
    public User createUser(@RequestBody @Valid User user) {
        log.debug("создаем пользователя: {}", user);
        users.put(user.getId(), user);
        return user;
    }


    @PutMapping("/{id}")
    public User updateUser(@PathVariable int id, @RequestBody User user1) {
        log.debug("обновляем пользователя: {}", user1);
        User user = users.get(id);

        if (user != null) {
            user.setName(user1.getName());
            user.setEmail(user1.getEmail());
            user.setBirthday(user1.getBirthday());
            users.put(id, user);
        }
        return user;
    }

    @GetMapping
    public List<User> getAllUsers() {
        return new ArrayList<>(users.values());
    }
}

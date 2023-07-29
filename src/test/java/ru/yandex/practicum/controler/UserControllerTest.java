package ru.yandex.practicum.controler;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.model.User;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class UserControllerTest {
    private final UserController userController = new UserController();
    User user = User.builder()
            .id(1)
            .email("test@email.com")
            .login("login")
            .name("name")
            .birthday(LocalDate.of(1997,10,10))
            .build();

    @Test
    void createUser() {
        userController.createUser(user);
        assertNotNull(userController.getAllUsers());
    }

    @Test
    void updateUser() {
        User user1 = User.builder()
                .id(1)
                .email("test@email.ru")
                .login("login")
                .name("name")
                .birthday(LocalDate.of(1997,10,10))
                .build();
        userController.createUser(user);
        userController.updateUser(user1);
        assertEquals(userController.getAllUsers().size(), 1);
    }
}
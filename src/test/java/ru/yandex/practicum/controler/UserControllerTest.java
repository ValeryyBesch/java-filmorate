package ru.yandex.practicum.controler;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.model.User;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class UserControllerTest {
    private Validator validator;
    private UserController userController;


    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void createUser() {
        User user = new User();
        user.setEmail("test@example.com");
        user.setLogin("testuser");
        user.setLogin("login");
        user.setName("");
        assertThrows(RuntimeException.class, ()-> userController.createUser(user));
    }

    @Test
    void updateUser() {

        User user = new User();
        user.setEmail("test@example.com");
        user.setLogin("testuser");
        user.setName("");
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertFalse(violations.isEmpty());
//        var violations = validator.validate(user);
//        assertNotNull(violations);
//
//        // Проверяем, что ошибка связана с полем name
//        var nameViolation = violations.stream()
//                .filter(v -> v.getPropertyPath().toString().equals("name"))
//                .findFirst()
//                .orElse(null);
//        assertNotNull(nameViolation);
//        assertEquals(nameViolation.getMessage() , "имя для отображения не может быть пустым");
    }

    @Test
    void getAllUsers() {
    }
}
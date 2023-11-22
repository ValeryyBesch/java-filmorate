package ru.yandex.practicum.exceotion;

public class NotValidationException extends RuntimeException {
    public NotValidationException(String message) {
        super(message);
    }
}

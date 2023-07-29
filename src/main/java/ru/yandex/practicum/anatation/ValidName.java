package ru.yandex.practicum.anatation;
import ru.yandex.practicum.model.User;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;


import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = ReleaseDateConstraintValidator.class)
public @interface ValidName {
    String message() default "Имя пустое, используем логин";

    Class<?>[] groups() default {};

    Class<?>[] payload() default {};
}
class DisplayNameValidator implements ConstraintValidator<ValidName, User> {

    @Override
    public boolean isValid(User user, ConstraintValidatorContext context) {
        if (user.getName() == null || user.getName().isEmpty()) {
            return user.getLogin() != null && !user.getLogin().isEmpty();
        }
        return true;
    }
}
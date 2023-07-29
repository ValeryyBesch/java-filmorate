package ru.yandex.practicum.anatation;
import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.time.ZoneId;
import java.util.Date;
import java.time.LocalDate;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = ReleaseDateConstraintValidator.class)
public @interface ValidReleaseDate {
    String message() default "Некорректная дата релиза";

    Class<?>[] groups() default {};

    Class<?>[] payload() default {};
}

class ReleaseDateConstraintValidator implements ConstraintValidator<ValidReleaseDate, Date> {

    private static final LocalDate MIN_RELEASE_DATE = LocalDate.of(1895, 12, 28);

    @Override
    public boolean isValid(Date date, ConstraintValidatorContext constraintValidatorContext) {
        if (date == null) {
            return true;
        }
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return !localDate.isBefore(MIN_RELEASE_DATE);
    }
}

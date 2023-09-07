package team.themoment.hellogsm.web.domain.application.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * null은 허용하지만 공백과 빈 문자열은 허용하지 않음
 */
@Documented
@Constraint(validatedBy = NotSpaceValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface NotSpace {
    String message() default "이 값을 공백 또는 비울 수 없습니다";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

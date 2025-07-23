package com.Livraria_Digital.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = AnoNaoFuturoValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface AnoNaoFuturo {
    String message() default "O ano não pode ser no futuro";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

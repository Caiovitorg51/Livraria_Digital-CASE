package com.Livraria_Digital.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import com.Livraria_Digital.validation.AnoNaoFuturo;

import java.time.Year;

public class AnoNaoFuturoValidator implements ConstraintValidator<AnoNaoFuturo, Integer> {

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        if (value == null) return true; // Deixe o @NotNull cuidar disso, se necessário

        int anoAtual = Year.now().getValue();
        return value <= anoAtual;
    }
}

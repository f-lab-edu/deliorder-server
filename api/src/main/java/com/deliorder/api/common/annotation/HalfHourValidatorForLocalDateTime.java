package com.deliorder.api.common.annotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDateTime;

public class HalfHourValidatorForLocalDateTime implements ConstraintValidator<HalfHourOnly, LocalDateTime> {

    @Override
    public boolean isValid(LocalDateTime localDateTime, ConstraintValidatorContext constraintValidatorContext) {
        if (localDateTime == null) return true;

        int minute = localDateTime.getMinute();
        return minute == 0 || minute == 30;
    }
}

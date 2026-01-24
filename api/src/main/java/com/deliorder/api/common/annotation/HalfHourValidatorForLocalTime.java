package com.deliorder.api.common.annotation;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalTime;

public class HalfHourValidatorForLocalTime implements ConstraintValidator<HalfHourOnly, LocalTime> {

    @Override
    public boolean isValid(LocalTime localTime, ConstraintValidatorContext constraintValidatorContext) {
        if (localTime == null) return true;

        int minute = localTime.getMinute();
        return minute == 0 || minute == 30;
    }
}

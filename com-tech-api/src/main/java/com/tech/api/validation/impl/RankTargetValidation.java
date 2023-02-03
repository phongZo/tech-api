package com.tech.api.validation.impl;

import com.tech.api.validation.RankTarget;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class RankTargetValidation implements ConstraintValidator<RankTarget, Long> {

    private boolean allowNull;

    @Override
    public void initialize(RankTarget constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        allowNull = constraintAnnotation.allowNull();
    }

    @Override
    public boolean isValid(Long target, ConstraintValidatorContext constraintValidatorContext) {
        if (target == null && allowNull) {
            return true;
        }
        if (target != null)
            return target >= 0;
        return false;
    }
}

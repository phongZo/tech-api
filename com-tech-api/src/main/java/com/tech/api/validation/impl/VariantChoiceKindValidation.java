package com.tech.api.validation.impl;

import com.tech.api.constant.Constants;
import com.tech.api.validation.VariantChoiceKind;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class VariantChoiceKindValidation implements ConstraintValidator<VariantChoiceKind, Integer> {

    private boolean allowNull;

    @Override
    public void initialize(VariantChoiceKind constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        this.allowNull = constraintAnnotation.allowNull();
    }

    @Override
    public boolean isValid(Integer choiceKind, ConstraintValidatorContext constraintValidatorContext) {
        if (choiceKind == null && allowNull) {
            return true;
        }

        if (choiceKind != null) {
            switch (choiceKind) {
                case Constants.VARIANT_SINGLE_CHOICE:
                case Constants.VARIANT_MULTIPLE_CHOICE:
                    return true;
                default:
                    return false;
            }
        }
        return false;
    }
}

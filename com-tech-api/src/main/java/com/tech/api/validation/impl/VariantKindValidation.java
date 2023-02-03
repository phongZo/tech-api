package com.tech.api.validation.impl;

import com.tech.api.constant.Constants;
import com.tech.api.validation.VariantKind;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class VariantKindValidation implements ConstraintValidator<VariantKind, Integer> {
    private boolean allowNull;

    @Override
    public void initialize(VariantKind constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        allowNull = constraintAnnotation.allowNull();
    }

    @Override
    public boolean isValid(Integer variantKind, ConstraintValidatorContext constraintValidatorContext) {
        if (variantKind == null && allowNull) {
            return true;
        }
        if (variantKind != null) {
            switch (variantKind) {
                case Constants.VARIANT_KIND_SIZE:
                case Constants.VARIANT_KIND_COLOR:
                case Constants.VARIANT_KIND_OTHER:
                    return true;
                default:
                    return false;
            }
        }
        return false;
    }
}

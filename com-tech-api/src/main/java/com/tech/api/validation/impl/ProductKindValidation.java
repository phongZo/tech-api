package com.tech.api.validation.impl;

import com.tech.api.constant.Constants;
import com.tech.api.validation.ProductKind;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ProductKindValidation implements ConstraintValidator<ProductKind, Integer> {

    private boolean allowNull;

    @Override
    public void initialize(ProductKind constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        allowNull = constraintAnnotation.allowNull();
    }

    @Override
    public boolean isValid(Integer productKind, ConstraintValidatorContext constraintValidatorContext) {
        if (productKind == null && allowNull) {
            return true;
        }
        if (productKind != null) {
            switch (productKind) {
                case Constants.PRODUCT_KIND_SINGLE:
                case Constants.PRODUCT_KIND_GROUP:
                    return true;
                default:
                    return false;
            }
        }
        return false;
    }
}

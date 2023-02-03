package com.tech.api.validation.impl;

import com.tech.api.constant.Constants;
import com.tech.api.validation.LocationKind;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class LocationKindValidation implements ConstraintValidator<LocationKind, Integer> {
    private boolean allowNull;

    @Override
    public void initialize(LocationKind constraintAnnotation) {
        allowNull = constraintAnnotation.allowNull();
    }

    @Override
    public boolean isValid(Integer locationKind, ConstraintValidatorContext constraintValidatorContext) {
        if (locationKind == null && allowNull) {
            return true;
        }
        if (locationKind != null) {
            switch (locationKind) {
                case Constants.LOCATION_KIND_PROVINCE:
                case Constants.LOCATION_KIND_DISTRICT:
                case Constants.LOCATION_KIND_WARD:
                    return true;
                default:
                    return false;
            }
        }
        return false;
    }
}

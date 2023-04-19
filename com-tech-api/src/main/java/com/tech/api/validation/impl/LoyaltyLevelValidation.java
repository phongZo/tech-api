package com.tech.api.validation.impl;

import com.tech.api.constant.Constants;
import com.tech.api.validation.LoyaltyLevel;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class LoyaltyLevelValidation implements ConstraintValidator<LoyaltyLevel, Integer> {
    private boolean allowNull;

    @Override
    public void initialize(LoyaltyLevel constraintAnnotation) {
        allowNull = constraintAnnotation.allowNull();
    }

    @Override
    public boolean isValid(Integer loyaltyLevel, ConstraintValidatorContext constraintValidatorContext) {
        if (loyaltyLevel == null && allowNull) {
            return true;
        }
        if (loyaltyLevel != null) {
            switch (loyaltyLevel) {
                case Constants.LOYALTY_LEVEL_BRONZE:
                case Constants.LOYALTY_LEVEL_SILVER:
                case Constants.LOYALTY_LEVEL_GOLD:
                case Constants.LOYALTY_LEVEL_PLATINUM:
                case Constants.LOYALTY_LEVEL_DIAMOND:
                case Constants.LOYALTY_LEVEL_ROYAL:
                    return true;
                default:
                    return false;
            }
        }
        return false;
    }
}

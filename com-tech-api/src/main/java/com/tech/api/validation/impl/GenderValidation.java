package com.tech.api.validation.impl;

import com.tech.api.constant.Constants;
import com.tech.api.validation.Gender;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

public class GenderValidation implements ConstraintValidator<Gender, Integer> {
    private boolean allowNull;

    @Override
    public void initialize(Gender constraintAnnotation) {
        allowNull = constraintAnnotation.allowNull();
    }

    @Override
    public boolean isValid(Integer gender, ConstraintValidatorContext constraintValidatorContext) {
        if(gender == null && allowNull){
            return true;
        }
        if(!Objects.equals(gender, Constants.GENDER_FEMALE)
                && !Objects.equals(gender, Constants.GENDER_MALE)
                && !Objects.equals(gender, Constants.GENDER_OTHER)){
            return false;
        }
        return true;
    }
}

package com.tech.api.validation.impl;

import com.tech.api.validation.Hashtag;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class HashtagValidation implements ConstraintValidator<Hashtag, String> {

    private boolean allowNull;

    @Override
    public void initialize(Hashtag constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        allowNull = constraintAnnotation.allowNull();
    }

    @Override
    public boolean isValid(String hashtag, ConstraintValidatorContext constraintValidatorContext) {
        if(StringUtils.isNoneBlank(hashtag)) {
            Pattern pattern = Pattern.compile("^[\\w_]+(?:\\s+[\\w_]+)*$", Pattern.UNICODE_CHARACTER_CLASS);
            return pattern.matcher(hashtag).find();
        }
        return allowNull;
    }
}

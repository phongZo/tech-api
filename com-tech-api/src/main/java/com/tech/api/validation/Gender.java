package com.tech.api.validation;

import com.tech.api.validation.impl.GenderValidation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = GenderValidation.class)
@Documented
public @interface Gender{
    boolean allowNull() default true;
    String message() default "Gender invalid.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

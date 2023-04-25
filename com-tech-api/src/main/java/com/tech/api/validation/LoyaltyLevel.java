package com.tech.api.validation;

import com.tech.api.validation.impl.LocationKindValidation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = LocationKindValidation.class)
@Documented
public @interface LoyaltyLevel {
    boolean allowNull() default false;
    String message() default  "Loyalty level invalid.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
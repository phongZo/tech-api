package com.tech.api.validation;

import com.tech.api.validation.impl.AmountOrdersDetailValidation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = AmountOrdersDetailValidation.class)
@Documented
public @interface AmountOrdersDetail {
    boolean allowNull() default false;
    String message() default  "Amount product invalid.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

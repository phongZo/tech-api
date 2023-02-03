package com.tech.api.validation;

import com.tech.api.validation.impl.ProductKindValidation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ProductKindValidation.class)
@Documented
public @interface ProductKind {
    boolean allowNull() default true;

    String message() default "Product kind is invalid.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

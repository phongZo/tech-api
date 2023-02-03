package com.tech.api.validation;

import com.tech.api.validation.impl.LocationKindValidation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = LocationKindValidation.class)
@Documented
public @interface LocationKind {
    boolean allowNull() default false;
    String message() default  "Location kind invalid.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

package com.tech.api.validation;

import com.tech.api.validation.impl.VariantChoiceKindValidation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = VariantChoiceKindValidation.class)
@Documented
public @interface VariantChoiceKind {
    boolean allowNull() default false;
    String message() default  "Variant template choice kind is invalid.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

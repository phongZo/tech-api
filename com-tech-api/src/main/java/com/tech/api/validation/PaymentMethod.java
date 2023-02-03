package com.tech.api.validation;

import com.tech.api.validation.impl.PaymentMethodValidation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PaymentMethodValidation.class)
@Documented
public @interface PaymentMethod {
    boolean allowNull() default true;
    String message() default "Payment method invalid.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

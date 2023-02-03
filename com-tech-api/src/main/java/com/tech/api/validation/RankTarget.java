package com.tech.api.validation;

import com.tech.api.validation.impl.RankTargetValidation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = RankTargetValidation.class)
@Documented
public @interface RankTarget {
    boolean allowNull() default false;
    String message() default  "Rank target is invalid.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

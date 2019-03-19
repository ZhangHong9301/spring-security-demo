package com.zxf.security.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Create by Mr.ZXF
 * on 2019-03-19 17:11
 */
@Target({ElementType.METHOD,ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = MyConstraintValidator.class)
public @interface MyConstraint {
    String message() default "{javax.validation.constraints.Past.message}";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

}

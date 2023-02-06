package com.leduyminh.commons.validator.annotation;

import com.leduyminh.commons.validator.FieldNotNullAndBlankValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = FieldNotNullAndBlankValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface FieldNotNullAndBlank {

    String message() default "Field not null and blank!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

package com.leduyminh.commons.validator;

import com.leduyminh.commons.validator.annotation.FieldNotNullAndBlank;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class FieldNotNullAndBlankValidator implements ConstraintValidator<FieldNotNullAndBlank, String> {

    @Override
    public void initialize(FieldNotNullAndBlank fieldNotNullAndBlank) {

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if (value == null || value.isEmpty() || value.equals(""))
        {
            return false;
        }
        return true;
    }
}

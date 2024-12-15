package com.ecommerce.utility;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import java.util.Set;

public class DtoValidatorUtil {

    private final static ValidatorFactory validatorFactory;
    private final static Validator validator;

    static {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    public static <T> void validate(T object) {
        Set<ConstraintViolation<T>> violations = validator.validate(object);
        if (!violations.isEmpty()) {
            throw new RuntimeException("Invalid JSON: " + violations.iterator().next().getPropertyPath() + " : " + violations.iterator().next().getMessage());
        }
    }

}

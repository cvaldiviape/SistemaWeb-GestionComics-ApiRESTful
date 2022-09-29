package com.mangagod.util.enums.validators;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EnumValidatorConstraint implements ConstraintValidator <EnumValidator, String> {

	Set<String> values;
	
	@Override
	public void initialize(EnumValidator constraintAnnotation) {
	    values = Stream.of(constraintAnnotation.enumClass().getEnumConstants())
	            .map(Enum::name)
	            .collect(Collectors.toSet());
	}
	
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
	    return values.contains(value);
	}
}

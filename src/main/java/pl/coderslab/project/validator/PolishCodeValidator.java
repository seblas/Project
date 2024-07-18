package pl.coderslab.project.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PolishCodeValidator implements ConstraintValidator<PolishCode, String> {

    @Override
    public void initialize(PolishCode constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if(value == null || value.isEmpty())  {
            return true;
        }
        return value.matches("\\d{2}-\\d{3}");
    }
}

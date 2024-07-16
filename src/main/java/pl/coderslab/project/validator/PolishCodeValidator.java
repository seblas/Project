package pl.coderslab.project.validator;

import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PolishCodeValidator implements ConstraintValidator<PolishCode, String> {

    @Override
    public void initialize(PolishCode constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        System.out.println("Validating postal code: " + value); // Logowanie
        if(value == null) {
            return true;
        }
        return value.matches("\\d{2}-\\d{3}");
    }
}

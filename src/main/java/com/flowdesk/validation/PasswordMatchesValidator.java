package com.flowdesk.validation;

import com.flowdesk.dto.RegisterRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, RegisterRequest> {

    @Override
    public boolean isValid(RegisterRequest request, ConstraintValidatorContext context) {
        if (request == null) {
            return true;
        }
        if (request.password() == null || request.confirmPassword() == null) {
            return false;
        }
        return request.password().equals(request.confirmPassword());
    }
}

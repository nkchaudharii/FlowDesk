package com.flowdesk.validation;

import com.flowdesk.dto.RegisterRequest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PasswordMatchesValidatorTest {

    @Test
    void shouldRejectDifferentPasswords() {
        PasswordMatchesValidator validator = new PasswordMatchesValidator();
        RegisterRequest request = new RegisterRequest("demo", "demo@example.com", "Demo User", "password123", "different");

        assertThat(validator.isValid(request, null)).isFalse();
    }

    @Test
    void shouldAcceptMatchingPasswords() {
        PasswordMatchesValidator validator = new PasswordMatchesValidator();
        RegisterRequest request = new RegisterRequest("demo", "demo@example.com", "Demo User", "password123", "password123");

        assertThat(validator.isValid(request, null)).isTrue();
    }
}

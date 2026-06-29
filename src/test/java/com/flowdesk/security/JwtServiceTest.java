package com.flowdesk.security;

import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.util.ReflectionTestUtils;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class JwtServiceTest {

    @Test
    void shouldGenerateAndValidateTokens() {
        JwtService jwtService = new JwtService();
        ReflectionTestUtils.setField(jwtService, "secret", Base64.getEncoder().encodeToString("flowdesk-test-secret-key-must-be-long-enough-for-hmac".getBytes(StandardCharsets.UTF_8)));
        ReflectionTestUtils.setField(jwtService, "accessTokenExpirationMs", 1800000L);
        ReflectionTestUtils.setField(jwtService, "refreshTokenExpirationMs", 604800000L);

        UserDetails userDetails = new User("demo", "password", List.of());

        String accessToken = jwtService.generateToken(userDetails);
        String refreshToken = jwtService.generateRefreshToken(userDetails);

        assertThat(jwtService.extractUsername(accessToken)).isEqualTo("demo");
        assertThat(jwtService.isTokenValid(accessToken, userDetails)).isTrue();
        assertThat(jwtService.extractUsername(refreshToken)).isEqualTo("demo");
    }
}

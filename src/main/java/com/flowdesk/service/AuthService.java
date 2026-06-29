package com.flowdesk.service;

import com.flowdesk.dto.AuthRequest;
import com.flowdesk.dto.AuthResponse;
import com.flowdesk.dto.RefreshTokenRequest;
import com.flowdesk.dto.RegisterRequest;
import jakarta.validation.Valid;

public interface AuthService {
    AuthResponse register(@Valid RegisterRequest request);

    AuthResponse login(@Valid AuthRequest request);

    AuthResponse refreshToken(@Valid RefreshTokenRequest request);

    void logout(String username);
}

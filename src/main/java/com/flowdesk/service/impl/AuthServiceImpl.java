package com.flowdesk.service.impl;

import com.flowdesk.dto.AuthRequest;
import com.flowdesk.dto.AuthResponse;
import com.flowdesk.dto.RefreshTokenRequest;
import com.flowdesk.dto.RegisterRequest;
import com.flowdesk.dto.UserProfileResponse;
import com.flowdesk.entity.Role;
import com.flowdesk.entity.User;
import com.flowdesk.exception.BusinessException;
import com.flowdesk.mapper.UserMapper;
import com.flowdesk.repository.UserRepository;
import com.flowdesk.security.JwtService;
import com.flowdesk.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserMapper userMapper;

    @Override
    @Transactional
    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByUsername(request.username())) {
            throw new BusinessException("Username is already taken");
        }
        if (userRepository.existsByEmail(request.email())) {
            throw new BusinessException("Email is already registered");
        }

        User user = User.builder()
                .username(request.username())
                .email(request.email())
                .fullName(request.fullName())
                .passwordHash(passwordEncoder.encode(request.password()))
                .role(Role.USER)
                .active(true)
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build();

        user = userRepository.save(user);

        return buildAuthResponse(user);
    }

    @Override
    @Transactional
    public AuthResponse login(AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.usernameOrEmail(), request.password())
        );

        User user = userRepository.findByUsernameOrEmail(request.usernameOrEmail(), request.usernameOrEmail())
                .orElseThrow(() -> new BusinessException("Invalid credentials"));

        user.setLastLoginAt(Instant.now());
        user.setUpdatedAt(Instant.now());
        userRepository.save(user);

        return buildAuthResponse(user);
    }

    @Override
    public AuthResponse refreshToken(RefreshTokenRequest request) {
        String username = jwtService.extractUsername(request.refreshToken());
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new BusinessException("User not found"));

        if (!jwtService.isTokenValid(request.refreshToken(), user)) {
            throw new BusinessException("Refresh token is invalid or expired");
        }

        return buildAuthResponse(user);
    }

    @Override
    public void logout(String username) {
        // No-op for stateless JWT implementation.
    }

    private AuthResponse buildAuthResponse(User user) {
        String accessToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);
        UserProfileResponse profile = userMapper.toProfileResponse(user);
        return new AuthResponse(accessToken, refreshToken, "Bearer", 1800, profile);
    }
}

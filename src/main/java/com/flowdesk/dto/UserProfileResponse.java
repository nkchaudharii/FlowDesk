package com.flowdesk.dto;

import com.flowdesk.entity.Role;

public record UserProfileResponse(
        Long id,
        String username,
        String email,
        String fullName,
        Role role
) {
}

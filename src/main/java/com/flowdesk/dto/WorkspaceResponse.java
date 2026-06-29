package com.flowdesk.dto;

import java.time.Instant;

public record WorkspaceResponse(
        Long id,
        String name,
        String slug,
        String description,
        UserProfileResponse owner,
        boolean active,
        Instant createdAt,
        Instant updatedAt
) {
}

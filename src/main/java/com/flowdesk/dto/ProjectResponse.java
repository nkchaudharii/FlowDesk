package com.flowdesk.dto;

import java.time.Instant;

public record ProjectResponse(
        Long id,
        Long workspaceId,
        String name,
        String keyName,
        String description,
        String status,
        Instant createdAt,
        Instant updatedAt
) {
}

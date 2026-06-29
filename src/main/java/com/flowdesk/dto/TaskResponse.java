package com.flowdesk.dto;

import java.time.Instant;
import java.time.LocalDate;

public record TaskResponse(
        Long id,
        Long projectId,
        String title,
        String description,
        String status,
        String priority,
        UserProfileResponse assignee,
        UserProfileResponse reporter,
        LocalDate dueDate,
        Instant createdAt,
        Instant updatedAt
) {
}

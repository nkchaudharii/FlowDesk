package com.flowdesk.dto;

import java.time.Instant;

public record CommentResponse(
        Long id,
        Long taskId,
        UserProfileResponse author,
        String content,
        Instant createdAt,
        Instant updatedAt
) {
}

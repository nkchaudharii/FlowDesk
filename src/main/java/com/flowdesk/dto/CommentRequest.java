package com.flowdesk.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CommentRequest(
        @NotBlank(message = "Comment content is required")
        @Size(min = 1, max = 4000, message = "Comment must be between 1 and 4000 characters")
        String content
) {
}

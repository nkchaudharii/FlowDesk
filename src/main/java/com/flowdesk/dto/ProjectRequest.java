package com.flowdesk.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ProjectRequest(
        @NotBlank(message = "Project name is required")
        @Size(min = 2, max = 150, message = "Project name must be between 2 and 150 characters")
        String name,

        @NotBlank(message = "Project key is required")
        @Size(min = 2, max = 20, message = "Project key must be between 2 and 20 characters")
        String keyName,

        @Size(max = 2000, message = "Description cannot exceed 2000 characters")
        String description,

        String status
) {
}

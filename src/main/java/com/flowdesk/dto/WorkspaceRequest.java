package com.flowdesk.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record WorkspaceRequest(
        @NotBlank(message = "Workspace name is required")
        @Size(min = 2, max = 150, message = "Workspace name must be between 2 and 150 characters")
        String name,

        @NotBlank(message = "Workspace slug is required")
        @Size(min = 2, max = 100, message = "Workspace slug must be between 2 and 100 characters")
        String slug,

        @Size(max = 1000, message = "Description cannot exceed 1000 characters")
        String description
) {
}

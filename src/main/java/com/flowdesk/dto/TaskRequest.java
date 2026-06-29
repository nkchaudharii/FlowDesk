package com.flowdesk.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record TaskRequest(
        @NotBlank(message = "Task title is required")
        @Size(min = 2, max = 200, message = "Task title must be between 2 and 200 characters")
        String title,

        @Size(max = 4000, message = "Description cannot exceed 4000 characters")
        String description,

        String status,

        String priority,

        Long assigneeId,

        LocalDate dueDate
) {
}

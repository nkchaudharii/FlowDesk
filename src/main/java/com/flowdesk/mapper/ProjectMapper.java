package com.flowdesk.mapper;

import com.flowdesk.dto.ProjectResponse;
import com.flowdesk.entity.Project;
import org.springframework.stereotype.Component;

@Component
public class ProjectMapper {

    public ProjectResponse toResponse(Project project) {
        if (project == null) {
            return null;
        }
        return new ProjectResponse(
                project.getId(),
                project.getWorkspace().getId(),
                project.getName(),
                project.getKeyName(),
                project.getDescription(),
                project.getStatus(),
                project.getCreatedAt(),
                project.getUpdatedAt()
        );
    }
}

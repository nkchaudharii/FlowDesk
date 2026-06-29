package com.flowdesk.mapper;

import com.flowdesk.dto.WorkspaceResponse;
import com.flowdesk.entity.Workspace;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WorkspaceMapper {

    private final UserMapper userMapper;

    public WorkspaceResponse toResponse(Workspace workspace) {
        if (workspace == null) {
            return null;
        }
        return new WorkspaceResponse(
                workspace.getId(),
                workspace.getName(),
                workspace.getSlug(),
                workspace.getDescription(),
                userMapper.toProfileResponse(workspace.getOwner()),
                workspace.isActive(),
                workspace.getCreatedAt(),
                workspace.getUpdatedAt()
        );
    }
}

package com.flowdesk.service;

import com.flowdesk.dto.WorkspaceRequest;
import com.flowdesk.dto.WorkspaceResponse;
import com.flowdesk.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface WorkspaceService {
    WorkspaceResponse createWorkspace(WorkspaceRequest request, User currentUser);

    Page<WorkspaceResponse> listWorkspaces(User currentUser, Pageable pageable);

    WorkspaceResponse getWorkspace(Long workspaceId, User currentUser);
}

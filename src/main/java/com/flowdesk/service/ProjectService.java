package com.flowdesk.service;

import com.flowdesk.dto.ProjectRequest;
import com.flowdesk.dto.ProjectResponse;
import com.flowdesk.entity.User;

import java.util.List;

public interface ProjectService {
    ProjectResponse createProject(Long workspaceId, ProjectRequest request, User currentUser);

    List<ProjectResponse> listProjects(Long workspaceId, User currentUser);
}

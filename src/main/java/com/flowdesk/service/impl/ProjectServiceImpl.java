package com.flowdesk.service.impl;

import com.flowdesk.dto.ProjectRequest;
import com.flowdesk.dto.ProjectResponse;
import com.flowdesk.entity.Project;
import com.flowdesk.entity.User;
import com.flowdesk.entity.Workspace;
import com.flowdesk.exception.BusinessException;
import com.flowdesk.exception.ResourceNotFoundException;
import com.flowdesk.mapper.ProjectMapper;
import com.flowdesk.repository.ProjectRepository;
import com.flowdesk.repository.WorkspaceMemberRepository;
import com.flowdesk.repository.WorkspaceRepository;
import com.flowdesk.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final WorkspaceRepository workspaceRepository;
    private final WorkspaceMemberRepository workspaceMemberRepository;
    private final ProjectMapper projectMapper;

    @Override
    @Transactional
    public ProjectResponse createProject(Long workspaceId, ProjectRequest request, User currentUser) {
        Workspace workspace = workspaceRepository.findById(workspaceId)
                .orElseThrow(() -> new ResourceNotFoundException("Workspace not found"));

        if (!hasAccess(workspace, currentUser)) {
            throw new BusinessException("You do not have access to this workspace");
        }

        Project project = Project.builder()
                .workspace(workspace)
                .name(request.name())
                .keyName(request.keyName())
                .description(request.description())
                .status(request.status() == null ? "ACTIVE" : request.status())
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build();

        return projectMapper.toResponse(projectRepository.save(project));
    }

    @Override
    public List<ProjectResponse> listProjects(Long workspaceId, User currentUser) {
        Workspace workspace = workspaceRepository.findById(workspaceId)
                .orElseThrow(() -> new ResourceNotFoundException("Workspace not found"));

        if (!hasAccess(workspace, currentUser)) {
            throw new BusinessException("You do not have access to this workspace");
        }

        return projectRepository.findAllByWorkspace(workspace).stream()
                .map(projectMapper::toResponse)
                .toList();
    }

    private boolean hasAccess(Workspace workspace, User currentUser) {
        return workspace.getOwner().getId().equals(currentUser.getId())
                || workspaceMemberRepository.existsByWorkspaceAndUser(workspace, currentUser);
    }
}

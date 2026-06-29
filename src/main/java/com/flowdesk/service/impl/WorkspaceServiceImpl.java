package com.flowdesk.service.impl;

import com.flowdesk.dto.WorkspaceRequest;
import com.flowdesk.dto.WorkspaceResponse;
import com.flowdesk.entity.Role;
import com.flowdesk.entity.User;
import com.flowdesk.entity.Workspace;
import com.flowdesk.entity.WorkspaceMember;
import com.flowdesk.exception.BusinessException;
import com.flowdesk.exception.ResourceNotFoundException;
import com.flowdesk.mapper.WorkspaceMapper;
import com.flowdesk.repository.WorkspaceMemberRepository;
import com.flowdesk.repository.WorkspaceRepository;
import com.flowdesk.service.WorkspaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class WorkspaceServiceImpl implements WorkspaceService {

    private final WorkspaceRepository workspaceRepository;
    private final WorkspaceMemberRepository workspaceMemberRepository;
    private final WorkspaceMapper workspaceMapper;

    @Override
    @Transactional
    public WorkspaceResponse createWorkspace(WorkspaceRequest request, User currentUser) {
        if (workspaceRepository.findBySlug(request.slug()).isPresent()) {
            throw new BusinessException("Workspace slug already exists");
        }

        Workspace workspace = Workspace.builder()
                .name(request.name())
                .slug(request.slug())
                .description(request.description())
                .owner(currentUser)
                .active(true)
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build();

        workspace = workspaceRepository.save(workspace);

        WorkspaceMember member = WorkspaceMember.builder()
                .workspace(workspace)
                .user(currentUser)
                .role(Role.ADMIN)
                .joinedAt(Instant.now())
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build();
        workspaceMemberRepository.save(member);

        return workspaceMapper.toResponse(workspace);
    }

    @Override
    public Page<WorkspaceResponse> listWorkspaces(User currentUser, Pageable pageable) {
        return workspaceRepository.findAllByOwner(currentUser, pageable).map(workspaceMapper::toResponse);
    }

    @Override
    public WorkspaceResponse getWorkspace(Long workspaceId, User currentUser) {
        Workspace workspace = workspaceRepository.findById(workspaceId)
                .orElseThrow(() -> new ResourceNotFoundException("Workspace not found"));

        boolean hasAccess = workspace.getOwner().getId().equals(currentUser.getId())
                || workspaceMemberRepository.existsByWorkspaceAndUser(workspace, currentUser);
        if (!hasAccess) {
            throw new BusinessException("You do not have access to this workspace");
        }

        return workspaceMapper.toResponse(workspace);
    }
}

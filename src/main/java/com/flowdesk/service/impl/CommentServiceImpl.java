package com.flowdesk.service.impl;

import com.flowdesk.dto.CommentRequest;
import com.flowdesk.dto.CommentResponse;
import com.flowdesk.entity.Comment;
import com.flowdesk.entity.Task;
import com.flowdesk.entity.User;
import com.flowdesk.entity.Workspace;
import com.flowdesk.exception.BusinessException;
import com.flowdesk.exception.ResourceNotFoundException;
import com.flowdesk.mapper.CommentMapper;
import com.flowdesk.repository.CommentRepository;
import com.flowdesk.repository.TaskRepository;
import com.flowdesk.repository.WorkspaceMemberRepository;
import com.flowdesk.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final TaskRepository taskRepository;
    private final WorkspaceMemberRepository workspaceMemberRepository;
    private final CommentMapper commentMapper;

    @Override
    @Transactional
    public CommentResponse createComment(Long taskId, CommentRequest request, User currentUser) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found"));

        if (!hasAccess(task.getProject().getWorkspace(), currentUser)) {
            throw new BusinessException("You do not have access to this workspace");
        }

        Comment comment = Comment.builder()
                .task(task)
                .author(currentUser)
                .content(request.content())
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build();

        return commentMapper.toResponse(commentRepository.save(comment));
    }

    @Override
    public List<CommentResponse> listComments(Long taskId, User currentUser) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found"));

        if (!hasAccess(task.getProject().getWorkspace(), currentUser)) {
            throw new BusinessException("You do not have access to this workspace");
        }

        return commentRepository.findAllByTaskOrderByCreatedAtAsc(task).stream()
                .map(commentMapper::toResponse)
                .toList();
    }

    private boolean hasAccess(Workspace workspace, User currentUser) {
        return workspace.getOwner().getId().equals(currentUser.getId())
                || workspaceMemberRepository.existsByWorkspaceAndUser(workspace, currentUser);
    }
}

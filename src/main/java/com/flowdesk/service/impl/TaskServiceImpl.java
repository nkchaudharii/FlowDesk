package com.flowdesk.service.impl;

import com.flowdesk.dto.TaskRequest;
import com.flowdesk.dto.TaskResponse;
import com.flowdesk.entity.Project;
import com.flowdesk.entity.Task;
import com.flowdesk.entity.User;
import com.flowdesk.entity.Workspace;
import com.flowdesk.exception.BusinessException;
import com.flowdesk.exception.ResourceNotFoundException;
import com.flowdesk.mapper.TaskMapper;
import com.flowdesk.repository.ProjectRepository;
import com.flowdesk.repository.TaskRepository;
import com.flowdesk.repository.UserRepository;
import com.flowdesk.repository.WorkspaceMemberRepository;
import com.flowdesk.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final WorkspaceMemberRepository workspaceMemberRepository;
    private final TaskMapper taskMapper;

    @Override
    @Transactional
    public TaskResponse createTask(Long projectId, TaskRequest request, User currentUser) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found"));

        Workspace workspace = project.getWorkspace();
        if (!hasAccess(workspace, currentUser)) {
            throw new BusinessException("You do not have access to this workspace");
        }

        User assignee = null;
        if (request.assigneeId() != null) {
            assignee = userRepository.findById(request.assigneeId())
                    .orElseThrow(() -> new ResourceNotFoundException("Assignee not found"));
        }

        Task task = Task.builder()
                .project(project)
                .title(request.title())
                .description(request.description())
                .status(parseStatus(request.status()))
                .priority(parsePriority(request.priority()))
                .assignee(assignee)
                .reporter(currentUser)
                .dueDate(request.dueDate())
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build();

        return taskMapper.toResponse(taskRepository.save(task));
    }

    @Override
    public Page<TaskResponse> listTasks(Long projectId, User currentUser, String search, String status, String priority, Pageable pageable) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found"));

        if (!hasAccess(project.getWorkspace(), currentUser)) {
            throw new BusinessException("You do not have access to this workspace");
        }

        List<Task> tasks = taskRepository.findAllByProject(project).stream()
                .filter(task -> search == null || search.isBlank() || containsSearch(task, search))
                .filter(task -> status == null || status.isBlank() || task.getStatus().name().equalsIgnoreCase(status))
                .filter(task -> priority == null || priority.isBlank() || task.getPriority().name().equalsIgnoreCase(priority))
                .sorted(Comparator.comparing(Task::getCreatedAt).reversed())
                .toList();

        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), tasks.size());
        if (start > end) {
            return new PageImpl<>(List.of(), pageable, tasks.size());
        }

        List<TaskResponse> pageItems = tasks.subList(start, end).stream()
                .map(taskMapper::toResponse)
                .toList();

        return new PageImpl<>(pageItems, pageable, tasks.size());
    }

    private Task.Status parseStatus(String status) {
        if (status == null || status.isBlank()) {
            return Task.Status.TODO;
        }
        try {
            return Task.Status.valueOf(status.toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException ex) {
            throw new BusinessException("Invalid task status");
        }
    }

    private Task.Priority parsePriority(String priority) {
        if (priority == null || priority.isBlank()) {
            return Task.Priority.MEDIUM;
        }
        try {
            return Task.Priority.valueOf(priority.toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException ex) {
            throw new BusinessException("Invalid task priority");
        }
    }

    private boolean containsSearch(Task task, String search) {
        String term = search.toLowerCase(Locale.ROOT);
        return task.getTitle().toLowerCase(Locale.ROOT).contains(term)
                || task.getDescription() != null && task.getDescription().toLowerCase(Locale.ROOT).contains(term);
    }

    private boolean hasAccess(Workspace workspace, User currentUser) {
        return workspace.getOwner().getId().equals(currentUser.getId())
                || workspaceMemberRepository.existsByWorkspaceAndUser(workspace, currentUser);
    }
}

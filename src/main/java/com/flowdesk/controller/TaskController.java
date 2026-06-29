package com.flowdesk.controller;

import com.flowdesk.dto.TaskRequest;
import com.flowdesk.dto.TaskResponse;
import com.flowdesk.entity.User;
import com.flowdesk.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Tag(name = "Tasks", description = "Task management APIs")
public class TaskController {

    private final TaskService taskService;

    @Operation(summary = "Create a task in a project")
    @PostMapping("/projects/{projectId}/tasks")
    public ResponseEntity<TaskResponse> createTask(@PathVariable Long projectId,
                                                   @Valid @RequestBody TaskRequest request,
                                                   @AuthenticationPrincipal User currentUser) {
        return ResponseEntity.status(HttpStatus.CREATED).body(taskService.createTask(projectId, request, currentUser));
    }

    @Operation(summary = "List and filter tasks in a project")
    @GetMapping("/projects/{projectId}/tasks")
    public ResponseEntity<Page<TaskResponse>> listTasks(@PathVariable Long projectId,
                                                         @AuthenticationPrincipal User currentUser,
                                                         @RequestParam(required = false) String search,
                                                         @RequestParam(required = false) String status,
                                                         @RequestParam(required = false) String priority,
                                                         @RequestParam(defaultValue = "0") int page,
                                                         @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(taskService.listTasks(projectId, currentUser, search, status, priority, pageable));
    }
}

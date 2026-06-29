package com.flowdesk.controller;

import com.flowdesk.dto.WorkspaceRequest;
import com.flowdesk.dto.WorkspaceResponse;
import com.flowdesk.entity.User;
import com.flowdesk.service.WorkspaceService;
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
@RequestMapping("/api/v1/workspaces")
@RequiredArgsConstructor
@Tag(name = "Workspaces", description = "Workspace management APIs")
public class WorkspaceController {

    private final WorkspaceService workspaceService;

    @Operation(summary = "Create a workspace")
    @PostMapping
    public ResponseEntity<WorkspaceResponse> createWorkspace(@Valid @RequestBody WorkspaceRequest request,
                                                             @AuthenticationPrincipal User currentUser) {
        return ResponseEntity.status(HttpStatus.CREATED).body(workspaceService.createWorkspace(request, currentUser));
    }

    @Operation(summary = "List workspaces for the current user")
    @GetMapping
    public ResponseEntity<Page<WorkspaceResponse>> listWorkspaces(@AuthenticationPrincipal User currentUser,
                                                                   @RequestParam(defaultValue = "0") int page,
                                                                   @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(workspaceService.listWorkspaces(currentUser, pageable));
    }

    @Operation(summary = "Get a workspace by id")
    @GetMapping("/{workspaceId}")
    public ResponseEntity<WorkspaceResponse> getWorkspace(@PathVariable Long workspaceId,
                                                          @AuthenticationPrincipal User currentUser) {
        return ResponseEntity.ok(workspaceService.getWorkspace(workspaceId, currentUser));
    }
}

package com.flowdesk.controller;

import com.flowdesk.dto.ProjectRequest;
import com.flowdesk.dto.ProjectResponse;
import com.flowdesk.entity.User;
import com.flowdesk.service.ProjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Tag(name = "Projects", description = "Project management APIs")
public class ProjectController {

    private final ProjectService projectService;

    @Operation(summary = "Create a project in a workspace")
    @PostMapping("/workspaces/{workspaceId}/projects")
    public ResponseEntity<ProjectResponse> createProject(@PathVariable Long workspaceId,
                                                         @Valid @RequestBody ProjectRequest request,
                                                         @AuthenticationPrincipal User currentUser) {
        return ResponseEntity.status(HttpStatus.CREATED).body(projectService.createProject(workspaceId, request, currentUser));
    }

    @Operation(summary = "List projects in a workspace")
    @GetMapping("/workspaces/{workspaceId}/projects")
    public ResponseEntity<List<ProjectResponse>> listProjects(@PathVariable Long workspaceId,
                                                              @AuthenticationPrincipal User currentUser) {
        return ResponseEntity.ok(projectService.listProjects(workspaceId, currentUser));
    }
}

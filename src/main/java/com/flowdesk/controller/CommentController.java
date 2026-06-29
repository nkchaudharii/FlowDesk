package com.flowdesk.controller;

import com.flowdesk.dto.CommentRequest;
import com.flowdesk.dto.CommentResponse;
import com.flowdesk.entity.User;
import com.flowdesk.service.CommentService;
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
@Tag(name = "Comments", description = "Task comment operations")
public class CommentController {

    private final CommentService commentService;

    @Operation(summary = "Add a comment to a task")
    @PostMapping("/tasks/{taskId}/comments")
    public ResponseEntity<CommentResponse> createComment(@PathVariable Long taskId,
                                                         @Valid @RequestBody CommentRequest request,
                                                         @AuthenticationPrincipal User currentUser) {
        return ResponseEntity.status(HttpStatus.CREATED).body(commentService.createComment(taskId, request, currentUser));
    }

    @Operation(summary = "List comments for a task")
    @GetMapping("/tasks/{taskId}/comments")
    public ResponseEntity<List<CommentResponse>> listComments(@PathVariable Long taskId,
                                                              @AuthenticationPrincipal User currentUser) {
        return ResponseEntity.ok(commentService.listComments(taskId, currentUser));
    }
}

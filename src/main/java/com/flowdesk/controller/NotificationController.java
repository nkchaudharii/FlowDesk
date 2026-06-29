package com.flowdesk.controller;

import com.flowdesk.dto.NotificationResponse;
import com.flowdesk.entity.User;
import com.flowdesk.service.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Tag(name = "Notifications", description = "Notification retrieval APIs")
public class NotificationController {

    private final NotificationService notificationService;

    @Operation(summary = "List notifications for the current user")
    @GetMapping("/notifications")
    public ResponseEntity<List<NotificationResponse>> listNotifications(@AuthenticationPrincipal User currentUser) {
        return ResponseEntity.ok(notificationService.listNotifications(currentUser));
    }
}

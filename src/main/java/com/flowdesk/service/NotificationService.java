package com.flowdesk.service;

import com.flowdesk.dto.NotificationResponse;
import com.flowdesk.entity.User;

import java.util.List;

public interface NotificationService {
    List<NotificationResponse> listNotifications(User currentUser);
}

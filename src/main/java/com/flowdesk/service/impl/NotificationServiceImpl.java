package com.flowdesk.service.impl;

import com.flowdesk.dto.NotificationResponse;
import com.flowdesk.entity.User;
import com.flowdesk.mapper.NotificationMapper;
import com.flowdesk.repository.NotificationRepository;
import com.flowdesk.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final NotificationMapper notificationMapper;

    @Override
    public List<NotificationResponse> listNotifications(User currentUser) {
        return notificationRepository.findAllByUserOrderByCreatedAtDesc(currentUser).stream()
                .map(notificationMapper::toResponse)
                .toList();
    }
}

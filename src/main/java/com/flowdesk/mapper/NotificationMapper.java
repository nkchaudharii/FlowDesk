package com.flowdesk.mapper;

import com.flowdesk.dto.NotificationResponse;
import com.flowdesk.entity.Notification;
import org.springframework.stereotype.Component;

@Component
public class NotificationMapper {

    public NotificationResponse toResponse(Notification notification) {
        if (notification == null) {
            return null;
        }
        return new NotificationResponse(
                notification.getId(),
                notification.getTitle(),
                notification.getMessage(),
                notification.getType(),
                notification.getLink(),
                notification.isRead(),
                notification.getCreatedAt()
        );
    }
}

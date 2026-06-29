package com.flowdesk.mapper;

import com.flowdesk.dto.TaskResponse;
import com.flowdesk.entity.Task;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TaskMapper {

    private final UserMapper userMapper;

    public TaskResponse toResponse(Task task) {
        if (task == null) {
            return null;
        }
        return new TaskResponse(
                task.getId(),
                task.getProject().getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus().name(),
                task.getPriority().name(),
                userMapper.toProfileResponse(task.getAssignee()),
                userMapper.toProfileResponse(task.getReporter()),
                task.getDueDate(),
                task.getCreatedAt(),
                task.getUpdatedAt()
        );
    }
}

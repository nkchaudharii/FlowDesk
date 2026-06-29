package com.flowdesk.service;

import com.flowdesk.dto.TaskRequest;
import com.flowdesk.dto.TaskResponse;
import com.flowdesk.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TaskService {
    TaskResponse createTask(Long projectId, TaskRequest request, User currentUser);

    Page<TaskResponse> listTasks(Long projectId, User currentUser, String search, String status, String priority, Pageable pageable);
}

package com.flowdesk.service;

import com.flowdesk.dto.CommentRequest;
import com.flowdesk.dto.CommentResponse;
import com.flowdesk.entity.User;

import java.util.List;

public interface CommentService {
    CommentResponse createComment(Long taskId, CommentRequest request, User currentUser);

    List<CommentResponse> listComments(Long taskId, User currentUser);
}

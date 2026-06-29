package com.flowdesk.mapper;

import com.flowdesk.dto.CommentResponse;
import com.flowdesk.entity.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommentMapper {

    private final UserMapper userMapper;

    public CommentResponse toResponse(Comment comment) {
        if (comment == null) {
            return null;
        }
        return new CommentResponse(
                comment.getId(),
                comment.getTask().getId(),
                userMapper.toProfileResponse(comment.getAuthor()),
                comment.getContent(),
                comment.getCreatedAt(),
                comment.getUpdatedAt()
        );
    }
}

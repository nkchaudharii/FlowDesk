package com.flowdesk.mapper;

import com.flowdesk.dto.UserProfileResponse;
import com.flowdesk.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserProfileResponse toProfileResponse(User user) {
        if (user == null) {
            return null;
        }
        return new UserProfileResponse(user.getId(), user.getUsername(), user.getEmail(), user.getFullName(), user.getRole());
    }
}

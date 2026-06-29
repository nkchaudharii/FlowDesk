package com.flowdesk.service;

import com.flowdesk.dto.UserProfileResponse;
import com.flowdesk.entity.User;

public interface UserService {
    UserProfileResponse getProfile(User currentUser);

    User getCurrentUserEntity(User currentUser);
}

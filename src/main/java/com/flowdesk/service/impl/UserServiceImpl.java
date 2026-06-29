package com.flowdesk.service.impl;

import com.flowdesk.dto.UserProfileResponse;
import com.flowdesk.entity.User;
import com.flowdesk.mapper.UserMapper;
import com.flowdesk.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;

    @Override
    public UserProfileResponse getProfile(User currentUser) {
        return userMapper.toProfileResponse(currentUser);
    }

    @Override
    public User getCurrentUserEntity(User currentUser) {
        return currentUser;
    }
}

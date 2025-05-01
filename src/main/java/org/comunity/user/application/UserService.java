package org.comunity.user.application;

import lombok.RequiredArgsConstructor;
import org.comunity.user.application.dto.CreateUserRequestDto;
import org.comunity.user.application.dto.GetUserResponseDto;
import org.comunity.user.application.interfaces.UserRepository;
import org.comunity.user.domain.User;
import org.comunity.user.domain.UserInfo;
import org.springframework.stereotype.Service;

import java.util.IllformedLocaleException;

// 다른 객체와 협업하는 역할
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User createUser(CreateUserRequestDto dto) {
        UserInfo userInfo = new UserInfo(dto.userName(), dto.userProfileUrl());
        User user = new User(null, userInfo);
        return userRepository.save(user);
    }

    public User getUser(Long id) {
        return userRepository.findById(id);
    }

    public GetUserResponseDto getUserProfile(Long id) {
        User user = getUser(id);
        return new GetUserResponseDto(user);
    }

}

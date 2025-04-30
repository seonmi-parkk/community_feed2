package org.comunity.auth.application;

import lombok.RequiredArgsConstructor;
import org.comunity.auth.application.dto.CreateUserAuthRequestDto;
import org.comunity.auth.application.interfaces.EmailVerificationRepository;
import org.comunity.auth.application.interfaces.UserAuthRepository;
import org.comunity.auth.domain.Email;
import org.comunity.auth.domain.UserAuth;
import org.comunity.user.domain.User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserAuthRepository userAuthRepository;
    private final EmailVerificationRepository verificationRepository;

    public Long registerUser(CreateUserAuthRequestDto dto) {
        Email email = Email.createEmail(dto.email());
        if (verificationRepository.isEmailVerified(email)) {
            throw new IllegalArgumentException("인증되지 않은 이메일입니다.");
        }

        UserAuth userAuth = new UserAuth(dto.email(), dto.password(), dto.role());
        User user = new User(dto.name(), dto.profileImageUrl());
        userAuth = userAuthRepository.registerUser(userAuth, user);

        return userAuth.getUserId();
    }

}

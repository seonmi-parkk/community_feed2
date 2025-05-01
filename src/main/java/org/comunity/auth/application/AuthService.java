package org.comunity.auth.application;

import lombok.RequiredArgsConstructor;
import org.comunity.auth.application.dto.CreateUserAuthRequestDto;
import org.comunity.auth.application.dto.LoginRequestDto;
import org.comunity.auth.application.dto.UserAccessTokenResponseDto;
import org.comunity.auth.application.interfaces.EmailVerificationRepository;
import org.comunity.auth.application.interfaces.UserAuthRepository;
import org.comunity.auth.domain.Email;
import org.comunity.auth.domain.TokenProvider;
import org.comunity.auth.domain.UserAuth;
import org.comunity.user.application.dto.CreateUserRequestDto;
import org.comunity.user.application.dto.GetUserResponseDto;
import org.comunity.user.application.interfaces.UserRepository;
import org.comunity.user.domain.User;
import org.comunity.user.domain.UserInfo;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final TokenProvider tokenProvider;
    private final EmailVerificationRepository emailVerificationRepository;
    private final UserAuthRepository userAuthRepository;

    public AuthService(
            TokenProvider tokenProvider,
            EmailVerificationRepository emailVerificationRepository,
            UserAuthRepository userAuthRepository
    ) {
        this.tokenProvider = tokenProvider;
        this.emailVerificationRepository = emailVerificationRepository;
        this.userAuthRepository = userAuthRepository;
    }


    public UserAccessTokenResponseDto registerUser(CreateUserAuthRequestDto dto) {
        Email email = Email.createEmail(dto.email());

        if (!emailVerificationRepository.isEmailVerified(email)) {
            throw new IllegalArgumentException("Email is not verified");
        }

        UserAuth userAuth = new UserAuth(dto.email(), dto.password(), dto.role());
        User user = new User(dto.name(), dto.profileImageUrl());
        userAuth = userAuthRepository.registerUser(userAuth, user);

        return createToken(userAuth);
    }

    public UserAccessTokenResponseDto loginUser(LoginRequestDto dto) {
        UserAuth userAuth = userAuthRepository.loginUser(dto.email(), dto.password());
        return createToken(userAuth);
    }

    private UserAccessTokenResponseDto createToken(UserAuth userAuth) {
        String token = tokenProvider.createToken(userAuth.getUserId(), userAuth.getRole());
        return new UserAccessTokenResponseDto(token);
    }

}

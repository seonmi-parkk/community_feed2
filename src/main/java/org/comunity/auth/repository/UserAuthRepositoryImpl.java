package org.comunity.auth.repository;

import lombok.RequiredArgsConstructor;
import org.comunity.auth.application.interfaces.UserAuthRepository;
import org.comunity.auth.domain.UserAuth;
import org.comunity.auth.repository.entity.UserAuthEntity;
import org.comunity.auth.repository.jpa.JpaUserAuthRepository;
import org.comunity.user.application.interfaces.UserRepository;
import org.comunity.user.domain.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class UserAuthRepositoryImpl implements UserAuthRepository {

    private final JpaUserAuthRepository jpaUserAuthRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserAuth registerUser(UserAuth userAuth, User user) {
        User savedUser = userRepository.save(user);
        UserAuthEntity userAuthEntity = new UserAuthEntity(userAuth, savedUser.getId());
        userAuthEntity = jpaUserAuthRepository.save(userAuthEntity);
        return userAuthEntity.toUserAuth();
    }

    @Override
    @Transactional
    public UserAuth loginUser(String email, String password) {
        UserAuthEntity userAuthEntity = jpaUserAuthRepository.findByEmail(email).orElseThrow();

        UserAuth userAuth = userAuthEntity.toUserAuth();
        if (!userAuth.matchPassword(password)) {
            throw new IllegalArgumentException("Invalid password");
        }

        // 마지막 로그인 시간 업데이트
        userAuthEntity.updateLastLoginAt();
        return userAuth;
    }

}

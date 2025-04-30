package org.comunity.auth.repository;

import lombok.RequiredArgsConstructor;
import org.comunity.auth.application.interfaces.UserAuthRepository;
import org.comunity.auth.domain.UserAuth;
import org.comunity.auth.repository.entity.UserAuthEntity;
import org.comunity.auth.repository.jpa.JpaUserAuthRepostiory;
import org.comunity.user.application.interfaces.UserRepository;
import org.comunity.user.domain.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class UserAuthRepositoryImpl implements UserAuthRepository {

    private final JpaUserAuthRepostiory jpaUserAuthRepostiory;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserAuth registerUser(UserAuth auth, User user) {
        User saveUser = userRepository.save(user);
        UserAuthEntity userAuthEntity = new UserAuthEntity(auth, saveUser.getId());
        userAuthEntity = jpaUserAuthRepostiory.save(userAuthEntity);
        return userAuthEntity.toUserAuth();
    }

}

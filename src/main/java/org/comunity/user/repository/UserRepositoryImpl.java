package org.comunity.user.repository;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.comunity.user.application.interfaces.UserRepository;
import org.comunity.user.domain.User;
import org.comunity.user.repository.entity.UserEntity;
import org.comunity.user.repository.jpa.JpaUserRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
// 클린 아키텍쳐 구조 지키기 위해 repository는 interface로 만들고 구현체를 만들어줌.
public class UserRepositoryImpl implements UserRepository {

    private final JpaUserRepository jpaUserRepository;

    @Override
    public User save(User user) {
        UserEntity entity = new UserEntity(user);
        entity = jpaUserRepository.save(entity);
        return entity.toUser();
    }

    @Override
    public User findById(Long id) {
        UserEntity entity = jpaUserRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        return entity.toUser();
    }
}

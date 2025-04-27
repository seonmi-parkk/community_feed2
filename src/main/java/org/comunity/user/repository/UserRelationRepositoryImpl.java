package org.comunity.user.repository;

import lombok.RequiredArgsConstructor;
import org.comunity.post.repository.post_queue.UserPostQueueCommandRepositoy;
import org.comunity.user.application.interfaces.UserRelationRepository;
import org.comunity.user.domain.User;
import org.comunity.user.repository.entity.UserEntity;
import org.comunity.user.repository.entity.UserRelationEntity;
import org.comunity.user.repository.entity.UserRelationIdEntity;
import org.comunity.user.repository.jpa.JpaUserRelationRepository;
import org.comunity.user.repository.jpa.JpaUserRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserRelationRepositoryImpl implements UserRelationRepository {

    private final JpaUserRelationRepository jpaUserRelationRepository;
    private final JpaUserRepository jpaUserRepository;
    private final UserPostQueueCommandRepositoy commandRepositoy;

    @Override
    public boolean isAlreadyFollow(User user, User targetUser) {
        UserRelationIdEntity id = new UserRelationIdEntity(user.getId(), targetUser.getId());
        return jpaUserRelationRepository.existsById(id);
    }

    @Override
    @Transactional
    public void save(User user, User targetUser) {
        UserRelationEntity entity = new UserRelationEntity(user.getId(), targetUser.getId());
        jpaUserRelationRepository.save(entity);

        // user와 targetUser의 팔로잉/팔로워 숫자 변경된 것 저장
        jpaUserRepository.saveAll(List.of(new UserEntity(user), new UserEntity(targetUser)));
        commandRepositoy.saveFollowPost(user.getId(), targetUser.getId());
    }

    @Override
    @Transactional
    public void delete(User user, User targetUser) {
        UserRelationIdEntity id = new UserRelationIdEntity(user.getId(), targetUser.getId());
        jpaUserRelationRepository.deleteById(id);
        // user와 targetUser의 팔로잉/팔로워 숫자 변경된 것 저장
        jpaUserRepository.saveAll(List.of(new UserEntity(user), new UserEntity(targetUser)));
        commandRepositoy.deleteUnfollowPost(user.getId(), targetUser.getId());
    }
}

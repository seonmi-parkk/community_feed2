package org.comunity.user.repository.jpa;

import org.comunity.user.repository.entity.UserRelationEntity;
import org.comunity.user.repository.entity.UserRelationIdEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface JpaUserRelationRepository extends JpaRepository<UserRelationEntity, UserRelationIdEntity> {

    // 나를 팔로우하는 유저리스트
    @Query("SELECT u.followingUserId FROM UserRelationEntity u WHERE u.followerUserId = :userId")
    List<Long> findFollowers(Long userId);
}

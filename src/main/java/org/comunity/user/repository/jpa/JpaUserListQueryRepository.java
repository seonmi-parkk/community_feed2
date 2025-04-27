package org.comunity.user.repository.jpa;

import org.comunity.user.application.dto.GetUserListResponseDto;
import org.comunity.user.repository.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface JpaUserListQueryRepository extends JpaRepository<UserEntity,Long> {

    // jpql 사용을 위한 어노테이션 // :userId (콜론: 변수임을 나타냄)
    // GetUserListResponseDto 형식에 맞게 데이터를 반환하기 위해 => new 해당 클래스
    // 만약 특정 데이터베이스에서만 제공하는 함수 사옹시 , nativeQuery = true 옵션 사용


    // 유저가 팔로잉한 사람들 리스트
    @Query(value = "SELECT new org.comunity.user.application.dto.GetUserListResponseDto(u.name, u.profileImage) " +
            "FROM UserRelationEntity ur " +
            "INNER JOIN UserEntity u ON ur.followerUserId = u.id " +
            "WHERE ur.followingUserId = :userId")
    List<GetUserListResponseDto> getFollowingUserList(Long userId);

    @Query(value = "SELECT new org.comunity.user.application.dto.GetUserListResponseDto(u.name, u.profileImage) " +
            "FROM UserRelationEntity ur " +
            "INNER JOIN UserEntity u ON ur.followingUserId = u.id " +
            "WHERE ur.followerUserId = :userId")
    List<GetUserListResponseDto> getFollowerUserList(Long userId);
}

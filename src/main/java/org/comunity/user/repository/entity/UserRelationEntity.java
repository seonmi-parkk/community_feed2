package org.comunity.user.repository.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.comunity.common.repository.entity.TimeBaseEntity;

@Entity
@Table(name = "community_user_relation")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@IdClass(UserRelationIdEntity.class)
public class UserRelationEntity extends TimeBaseEntity {
    // UserRelationEntity는 도메인 객체가 따로 존재하지 않음.
    // 두 유저의 아이디를 조합해서 관계를 만들며, 이 조함은 unique 함.
    // -> 두 유저의 아이디를 조합해서 pk를 만들기 (클러스터드 인덱스덕에 빠르게 조회& unique 함 보장)
    // 복합키 만드는 법 2가지
        // 1. 아이디엔티티 객체(UserRelationIdEntity) 만들고 칼럼으로 상속받기
    @Id
    private Long followingUserId;

    @Id
    private Long followerUserId;

}

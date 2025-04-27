package org.comunity.user.repository.jpa;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.comunity.user.application.dto.GetUserListResponseDto;
import org.comunity.user.repository.entity.QUserEntity;
import org.comunity.user.repository.entity.QUserRelationEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class JpaUserListPagingQueryRepository {
    // 페이징 처리 때문에 동적 쿼리 필요해서 querydsl 사용
    // querydsl을 사용하기 위해서 jpaQueryFactory를 주입받음
    private final JPAQueryFactory jpaQueryFactory;

    // 큐 객체들 불러오기
    // 유저관계에 따른 유저 정보들을 가지고 와야하기 때문에 2개의 큐 객체
    private static final QUserEntity user = QUserEntity.userEntity;
    private static final QUserRelationEntity relation = QUserRelationEntity.userRelationEntity;

    // lastFollowerId : 마지막으로 조회한 유저 아이디
    public List<GetUserListResponseDto> getFollowerList(Long userId, Long lastFollowerId) {
        return jpaQueryFactory
                .select(
                        // Projections.fields => querydsl에서 자동으로 DTO에 맞춘 데이터를 넣어줌.
                        Projections.fields(
                                GetUserListResponseDto.class

                        )
                ).from(relation)
                .join(user).on(relation.followingUserId.eq(user.id))
                .where(
                        relation.followerUserId.eq(userId)
                        ,hasLastData(lastFollowerId)
                )
                .orderBy(user.id.desc())
                .limit(20)
                .fetch();
    }

    // lastFollowerId가 있다면 유저의 lastFollowerId 보다 작은값들을 계속해서 가지고 올수 있도록 함.
    // dynamicQuery 이용
    private BooleanExpression hasLastData(Long lastId) {
        if(lastId == null){
            return null;
        }
        return user.id.lt(lastId);
    }

}

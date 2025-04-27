package org.comunity.post.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.comunity.post.repository.entity.comment.QCommentEntity;
import org.comunity.post.repository.entity.like.LikeTarget;
import org.comunity.post.repository.entity.like.QLikeEntity;
import org.comunity.post.ui.dto.GetContentResponseDto;
import org.comunity.user.repository.entity.QUserEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CommentQueryRepositoryImpl {

    private final JPAQueryFactory queryFactory;
    private static final QCommentEntity commentEntity = QCommentEntity.commentEntity;
    private static final QUserEntity userEntity = QUserEntity.userEntity;
    private static final QLikeEntity likeEntity = QLikeEntity.likeEntity;

    public List<GetContentResponseDto> getCommentList(Long postId, Long userId, Long lastContentId) {
        return queryFactory
                .select(
                        Projections.fields(
                                GetContentResponseDto.class,
                                commentEntity.id.as("id"),
                                commentEntity.content.as("content"),
                                userEntity.id.as("userId"),
                                userEntity.name.as("userName"),
                                userEntity.profileImage.as("userProfileImage"),
                                commentEntity.likeCount.as("likeCount"),
                                commentEntity.regDt.as("createdAt"),
                                commentEntity.updDt.as("updatedAt"),
                                likeEntity.isNotNull().as("isLikeByMe")
                        )
                )
                .from(commentEntity)
                .join(userEntity).on(commentEntity.author.id.eq(userEntity.id))
                .leftJoin(likeEntity).on(hasLike(userId))
                .where(
                        commentEntity.post.id.eq(postId),
                        hasLastData(lastContentId)
                )
                .orderBy(commentEntity.id.desc())
                .limit(10)
                .fetch();
    }

    private BooleanExpression hasLastData(Long lastId) {
        if (lastId == null) {
            return null;
        }

        return commentEntity.id.lt(lastId);
    }

    private BooleanExpression hasLike(Long userId) {
        if (userId == null) {
            return null;
        }

        return commentEntity.id
                .eq(likeEntity.id.targetId)
                .and(likeEntity.id.targetType.eq(LikeTarget.COMMENT.name()))
                .and(likeEntity.id.userId.eq(userId));
    }

}

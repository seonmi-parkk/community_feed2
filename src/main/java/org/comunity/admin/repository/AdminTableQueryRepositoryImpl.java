package org.comunity.admin.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.comunity.admin.ui.dto.GetTableListResponseDto;
import org.comunity.admin.ui.dto.posts.GetPostTableRequestDto;
import org.comunity.admin.ui.dto.posts.GetPostTableResponseDto;
import org.comunity.admin.ui.dto.users.GetUserTableRequestDto;
import org.comunity.admin.ui.dto.users.GetUserTableResponseDto;
import org.comunity.admin.ui.query.AdminTableQueryRepository;
import org.comunity.auth.repository.entity.QUserAuthEntity;
import org.comunity.post.repository.entity.post.QPostEntity;
import org.comunity.user.repository.entity.QUserEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class AdminTableQueryRepositoryImpl implements AdminTableQueryRepository {
    private final JPAQueryFactory queryFactory;
    private static final QUserAuthEntity userAuthEntity = QUserAuthEntity.userAuthEntity;
    private static final QUserEntity userEntity = QUserEntity.userEntity;
    private static final QPostEntity postEntity = QPostEntity.postEntity;

    @Override
    public GetTableListResponseDto<GetUserTableResponseDto> getUserTableData(GetUserTableRequestDto dto) {
        int total = queryFactory.select(userEntity.id)
                .from(userEntity)
                .where(likeName(dto.getName()))
                .fetch()
                .size();

        // id만 가져오는 쿼리 -> 커버링 인덱스 적용됨
        // 1. id만 가져오는 쿼리
            // 커버링 인덱스로 id만 빠르게 가져오기 때문에 기존에 50만개 데이터를 모두 가져오는 것에 비해  데이터가 많아지는 것에 대한 부하가 적어짐.
            // 조건절에 likeName이 들어가면 name 인덱스를 추가로 넣어줘야 하지만 지금은 이름을 검색하는 조건은 아니기때문에 성능만 확인
        // 2. 클러스터 인덱스에 접근해서 id값들에 대한 데이터들을 가져오는 쿼리

        List<Long> ids = queryFactory.select(userEntity.id)
                .from(userEntity)
                .where(
                        likeName(dto.getName())
                ).orderBy(userEntity.id.desc())
                .offset(dto.getOffset())
                .limit(dto.getLimit())
                .fetch();

        List<GetUserTableResponseDto> result = queryFactory
                .select(
                        Projections.fields(
                                GetUserTableResponseDto.class,
                                userEntity.id.as("id"),
                                userAuthEntity.email.as("email"),
                                userEntity.name.as("name"),
                                userAuthEntity.role.as("role"),
                                userEntity.regDt.as("createdAt"),
                                userEntity.updDt.as("updatedAt"),
                                userAuthEntity.lastLoginDt.as(("lastLoginAt"))
                        )
                ).from(userEntity)
                .join(userAuthEntity).on(userAuthEntity.userId.eq(userEntity.id))
                .where(
                        userEntity.id.in(ids)
                ).orderBy(userEntity.id.desc())
                .fetch();
        return new GetTableListResponseDto<>(total, result);
    }

    @Override
    public GetTableListResponseDto<GetPostTableResponseDto> getPostTableData(GetPostTableRequestDto dto) {
        int total = queryFactory.select(postEntity.id)
                .from(postEntity)
                .where(
                        eqPostId(dto.getPostId())
                )
                .fetch()
                .size();

        List<Long> ids = queryFactory
                .select(postEntity.id)
                .from(postEntity)
                .where(
                        eqPostId(dto.getPostId())
                )
                .orderBy(postEntity.id.desc())
                .offset(dto.getOffset())
                .limit(dto.getLimit())
                .fetch();

        List<GetPostTableResponseDto> result = queryFactory
                .select(
                        Projections.fields(
                            GetPostTableResponseDto.class,
                            postEntity.id.as("postId"),
                            userEntity.id.as("userId"),
                            userEntity.name.as("userName"),
                            postEntity.content.as("content"),
                            postEntity.regDt.as("createdAt"),
                            postEntity.updDt.as("updatedAt")
                        )
                )
                .from(postEntity)
                .join(userEntity).on(postEntity.author.id.eq(userEntity.id))
                .where(
                        postEntity.id.in(ids)
                )
                .orderBy(postEntity.id.desc())
                .fetch();

        return new GetTableListResponseDto<>(total, result);
    }

    private BooleanExpression likeName(String name) {
        if (name == null || name.isBlank()) {
            return null;
        }
        return userEntity.name.like("%" + name + "%");
    }

    private BooleanExpression eqPostId(Long Id) {
        if (Id == null) {
            return null;
        }
        return postEntity.id.eq(Id);
    }
}

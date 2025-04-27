package org.comunity.post.repository.jpa;

import org.comunity.post.repository.entity.post.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface JpaPostRepository extends JpaRepository<PostEntity, Long> {

    // 유저 아이디로 모든 포스트 조회 (새롭게 팔로우 한 유저가 작성한글을 UserPostQueue에 저장하기위해)
    @Query("SELECT p.id FROM PostEntity p WHERE p.author.id = :authorId")
    List<Long> findAllPostIdsByAuthorId(Long authorId);

    @Modifying
    @Query(value="UPDATE PostEntity p "// #{#postEntity.content} => 객체 안의 값 가져오기
            + "SET p.content = :#{#postEntity.content},"
            + "p.updDt = now() "
            + "WHERE p.id = :#{#postEntity.id}")
    void updatePostEntity(PostEntity postEntity);

    @Modifying
    @Query(value = "UPDATE PostEntity p "
            + "SET p.likeCount = p.likeCount + :likeCount, "
            + "p.updDt = now() " // 직접 jpql을 작성하는 경우 @LastModifiedDate 동작이 안되기때문에 작성해줘야함.
            + "WHERE p.id = :postId")
    void updateLikeCount(Long postId, Integer likeCount);

    @Modifying
    @Query(value = "UPDATE PostEntity p "
            + "SET p.commentCount = p.commentCount + 1,"
            + "p.updDt = now() "
            + "WHERE p.id = :id")
    void increaseCommentCount(Long id);
}

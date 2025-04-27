package org.comunity.post.repository.jpa;

import org.comunity.post.repository.entity.comment.CommentEntity;
import org.comunity.post.repository.entity.post.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface JpaCommentRepository extends JpaRepository<CommentEntity, Long> {

    @Modifying
    @Query(value = "UPDATE CommentEntity c "
            + "SET c.content = :#{#comment.getContent()},"
            + "c.updDt = now() "
            + "WHERE c.id = :#{#comment.getId()}")
    void updateCommentEntity(CommentEntity comment);

    @Modifying
    @Query(value = "UPDATE CommentEntity c "
            + "SET c.likeCount = c.likeCount +:likeCount,"
            + "c.updDt = now() " // 직접 jpql을 작성하는 경우 @LastModifiedDate 동작이 안되기때문에 작성해줘야함.
            + "WHERE c.id = :commentId")
    void updateLikeCount(Long commentId, Integer likeCount);
}

package org.comunity.post.repository.entity.like;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.comunity.common.repository.entity.TimeBaseEntity;
import org.comunity.post.domain.Post;
import org.comunity.post.domain.comment.Comment;
import org.comunity.user.domain.User;

@Entity
@Table(name = "community_like")
@NoArgsConstructor
@Getter
public class LikeEntity extends TimeBaseEntity {
    // userRelationIdEntity와 거의 비슷
        // 게시물/댓글 아이디 + 유저 아이디 조합
        // => 게시물 아이디인지 댓글 아이디인지 구분하는 구분자만 추가되면 됨.
        // 구분자값을 엔티티에서 구분하기 위한 enum (LikeTarget)

    // UserRelationEntity에서 @IdClass(UserRelationIdEntity.class)를 달아준것과 달리
    // 여기서는 @EmbeddedId를 사용해서 3개의 컬럼을 복합키로 구성
    @EmbeddedId
    private LikeIdEntity id;

    // 생성시 받은 객체 타입(Post, Comment)에 따라서 생성자 구분
    public LikeEntity(Post post, User likedUser) {
        this.id = new LikeIdEntity(post.getId(), likedUser.getId(), LikeTarget.POST.name());
    }

    public LikeEntity(Comment comment, User likedUser) {
        this.id = new LikeIdEntity(comment.getId(), likedUser.getId(), LikeTarget.COMMENT.name());
    }




}

package org.comunity.post.repository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.comunity.post.application.interfaces.CommentRepository;
import org.comunity.post.domain.Post;
import org.comunity.post.domain.comment.Comment;
import org.comunity.post.repository.entity.comment.CommentEntity;
import org.comunity.post.repository.jpa.JpaCommentRepository;
import org.comunity.post.repository.jpa.JpaPostRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CommentRepositoryImpl implements CommentRepository {

    private final JpaCommentRepository jpaCommentRepository;
    private final JpaPostRepository jpaPostRepository;

    @Override
    @Transactional
    public Comment save(Comment comment) {
        // 댓글 추가 될때 commentCount 1 증가
        // 좋아요 때도 같은 요구사항 ⇒ 좋아요는 도메인을 update하는 형식으로 했었음
        // 이번에는 도메인으로 넣지않고 commentCount넣어서 엔티티로만 구현
        // 두 방식의 차이점 고민해보기
        Post targetPost = comment.getPost();
        CommentEntity commentEntity = new CommentEntity(comment);
        if(comment.getId() != null) {
            jpaCommentRepository.updateCommentEntity(commentEntity);
            return comment;
        }

        commentEntity = jpaCommentRepository.save(commentEntity);
        jpaPostRepository.increaseCommentCount(targetPost.getId());
        return commentEntity.toComment();
    }

    @Override
    public Comment findById(Long id) {
        CommentEntity commentEntity = jpaCommentRepository.findById(id).orElseThrow();
        return commentEntity.toComment();
    }
}

package org.comunity.post.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.comunity.post.application.interfaces.LikeRepository;
import org.comunity.post.domain.Post;
import org.comunity.post.domain.comment.Comment;
import org.comunity.post.repository.entity.comment.CommentEntity;
import org.comunity.post.repository.entity.like.LikeEntity;
import org.comunity.post.repository.entity.post.PostEntity;
import org.comunity.post.repository.jpa.JpaCommentRepository;
import org.comunity.post.repository.jpa.JpaLikeRepository;
import org.comunity.post.repository.jpa.JpaPostRepository;
import org.comunity.user.domain.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class LikeRepositoryImpl implements LikeRepository {

    // 전체적으로 likeEntity는 전부 새로 생기거나 삭제되는 것밖에 없음
    // spring data JPA의 경우 save() 할경우 merge가 발생하기 때문에
    // 항상 저장하기 전에 있는지 조회하고 저장하는 일이 발생
    // 여기서는 항상 insert문만 발생할 수 있도록 entityManager를 직접 추가해서
    // jpaLikeRepository.save(likeEntity); 대신
    // entityManager.persist(likeEntity); 사용하면 불필요한 조회를 하지 않음.
    // entityManager.persist(likeEntity); 로 진행시 만약 같은 아이디가 있으면
    // db에서 primariy key는 하나밖에 존재할 수 없기때문에 duplicated key exception이 발생함.
    // 그렇기때문에 지금은 테이블에 2개의 pk가 들어가지 않게 보장됨.
    @PersistenceContext
    private final EntityManager entityManager;

    private final JpaPostRepository jpaPostRepository;
    private final JpaCommentRepository jpaCommentRepository;
    private final JpaLikeRepository jpaLikeRepository;

    @Override
    public boolean checkLike(Post post, User user) {
        LikeEntity likeEntity = new LikeEntity(post, user);
        return jpaLikeRepository.existsById(likeEntity.getId());
    }

    @Override
    @Transactional
    public void like(Post post, User user) {
        LikeEntity likeEntity = new LikeEntity(post, user);
        //jpaLikeRepository.save(likeEntity);
        entityManager.persist(likeEntity);
        jpaPostRepository.updateLikeCount(post.getId(), 1);
    }

    @Override
    @Transactional
    public void unlike(Post post, User user) {
        LikeEntity likeEntity = new LikeEntity(post, user);
        jpaLikeRepository.deleteById(likeEntity.getId());
        jpaPostRepository.updateLikeCount(post.getId(), -1);
    }

    @Override
    public boolean checkLike(Comment comment, User user) {
        LikeEntity likeEntity = new LikeEntity(comment, user);
        return jpaLikeRepository.existsById(likeEntity.getId());
    }

    @Override
    @Transactional
    public void like(Comment comment, User user) {
        LikeEntity likeEntity = new LikeEntity(comment, user);
        //jpaLikeRepository.save(likeEntity);
        entityManager.persist(likeEntity);
        jpaCommentRepository.updateLikeCount(comment.getId(), 1);
    }

    @Override
    @Transactional
    public void unlike(Comment comment, User user) {
        LikeEntity likeEntity = new LikeEntity(comment, user);
        jpaLikeRepository.deleteById(likeEntity.getId()); // 얘도 두번조회되기 때문에 수정필요
        jpaCommentRepository.updateLikeCount(comment.getId(), -1);
    }
}

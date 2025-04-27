package org.comunity.post.repository;

import lombok.RequiredArgsConstructor;
import org.comunity.post.application.interfaces.PostRepository;
import org.comunity.post.domain.Post;
import org.comunity.post.repository.entity.post.PostEntity;
import org.comunity.post.repository.jpa.JpaPostRepository;
import org.comunity.post.repository.post_queue.UserPostQueueCommandRepositoy;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepository {

    private final JpaPostRepository jpaPostRepository;
    private final UserPostQueueCommandRepositoy commandRepository;

    @Override
    // update쿼리 2번 수행되는 문제 해결 방법 1번
        // Transactional 선언을 하고 트랜잭션안에서 PostEntity를 조회하고
        // 비즈니스 로직을 이 안에서 수행시킴
        // 하지만 이렇게 되면 postid, userid를 받아와야함.
    // 현재로썬 변경사항이 많아서 2번 방법을 사용할 것임.
    // 문제 해결 방법 2번
        // update문을 jpql 쿼리문으로 작성
        // save메서드에서 update인경우 작성한 jpql 쿼리문 실행
        //  @Transactional 어노테이션 추가하고
        // jpql쿼리에  @Modifying 추가해야 변경이 저장되고 변경 사용 가능
    @Transactional
    public Post save(Post post) {
        PostEntity postEntity = new PostEntity(post);
        if(post.getId() != null) {
            // update인 경우
            jpaPostRepository.updatePostEntity(postEntity);
            return postEntity.toPost();
        }
        postEntity = jpaPostRepository.save(postEntity);
        commandRepository.publishPost(postEntity);
        return postEntity.toPost();
    }

    @Override
    @Transactional
    public Post findById(Long id) {
        PostEntity postEntity = jpaPostRepository.findById(id).orElseThrow();
        return postEntity.toPost();
    }
}

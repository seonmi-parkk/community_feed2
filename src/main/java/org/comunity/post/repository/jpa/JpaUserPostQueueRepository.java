package org.comunity.post.repository.jpa;

import org.comunity.post.repository.entity.post.UserPostQueueEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaUserPostQueueRepository extends JpaRepository<UserPostQueueEntity, Long> {

    // jpa 쿼리메서드 이용
    void deleteAllByUserIdAndAuthorId(Long userId, Long targetId);
}

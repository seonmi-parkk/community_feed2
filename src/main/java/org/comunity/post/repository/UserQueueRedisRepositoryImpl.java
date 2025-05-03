package org.comunity.post.repository;

import org.comunity.post.repository.entity.post.PostEntity;
import org.comunity.post.repository.post_queue.UserQueueRedisRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserQueueRedisRepositoryImpl implements UserQueueRedisRepository {
    @Override
    public void publishPostToFollowingUserList(PostEntity postEntity, List<Long> userIdList) {

    }

    @Override
    public void publishPostListToFollowerUser(List<PostEntity> postEntityList, Long userId) {

    }

    @Override
    public void deleteDeleteFeed(Long userId, Long authorId) {

    }
}

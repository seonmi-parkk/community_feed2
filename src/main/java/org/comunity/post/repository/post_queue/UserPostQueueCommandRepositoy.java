package org.comunity.post.repository.post_queue;

import org.comunity.post.repository.entity.post.PostEntity;

// 이 인터페이스를 repository 하위에 넣은 이유
    // 이 인터페이스는 service 레이어에 노출이 되어서는 안되기 때문에
    // 서비스 레이어는 레포,도메인,컨트롤러에서 오는 데이터를 전달하고 가공하는 중간다리 역할
    // 트랜잭션 보장은 레포에서 하기로 했음.
    // 그렇기 때문에 트랜잭션을 담당하는 레포에서 직접 주입하고 해결하는것이 좋음.
public interface UserPostQueueCommandRepositoy {
    void publishPost(PostEntity postEntity);
    void saveFollowPost(Long userId,Long targetId);
    void deleteUnfollowPost(Long userId,Long targetId);
}

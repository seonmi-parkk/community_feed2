package org.comunity.post.repository.post_queue;

import org.comunity.post.ui.dto.GetPostContentResponseDto;

import java.util.List;

public interface UserPostQueueQueryRepository {
    List<GetPostContentResponseDto> getContentResponse(Long userId, Long lastPostId);
}

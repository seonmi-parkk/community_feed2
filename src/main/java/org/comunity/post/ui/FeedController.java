package org.comunity.post.ui;

import lombok.RequiredArgsConstructor;
import org.comunity.common.ui.Response;
import org.comunity.post.repository.post_queue.UserPostQueueQueryRepository;
import org.comunity.post.ui.dto.GetPostContentResponseDto;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/feed")
@RequiredArgsConstructor
public class FeedController {

    private final UserPostQueueQueryRepository queueQueryRepository;

    @GetMapping("/{userId}")
    public Response<List<GetPostContentResponseDto>> getPostFeed(@PathVariable(name="userId") Long userId, Long lastPostId) {
        List<GetPostContentResponseDto> result = queueQueryRepository.getContentResponse(userId, lastPostId);
        return Response.ok(result);
    }
}

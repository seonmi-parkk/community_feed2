package org.comunity.post.ui;

import lombok.RequiredArgsConstructor;
import org.comunity.common.Principal.AuthPrincipal;
import org.comunity.common.Principal.UserPrincipal;
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

    @GetMapping
    public Response<List<GetPostContentResponseDto>> getPostFeed(@AuthPrincipal UserPrincipal userPrincipal, Long lastPostId) {
        List<GetPostContentResponseDto> result = queueQueryRepository.getContentResponse(userPrincipal.getUserId(), lastPostId);
        return Response.ok(result);
    }
}

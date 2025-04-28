package org.comunity.acceptance;

import org.comunity.acceptance.steps.FeedAcceptanceSteps;
import org.comunity.acceptance.utils.AcceptanceTestTemplate;
import org.comunity.post.application.dto.CreatePostRequestDto;
import org.comunity.post.domain.PostPublicationState;
import org.comunity.post.ui.dto.GetPostContentResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.comunity.acceptance.steps.FeedAcceptanceSteps.requestFeed;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class FeedAcceptanceTest extends AcceptanceTestTemplate {

    /**
     * User 1 --- follow ---> User 2
     * User 1 --- follow ---> User 3
     */
    @BeforeEach
    void setUp() {
        super.init();
    }

    // 인수테스트는 다양한 상황과 복잡한 셋팅이 있기 때문에
    // 데이터 베이스 셋팅과 어떤 결과 값을 테스트 해봐야 하는지 작성 해두는 것이 유지보수에 도움
    /**
     * User 2 create Post 1
     * User 1 Get Post 1 From Feed
     */
    @Test
    void givenUserHasFollowerAndCreatePost_whenFollowerUserRequestFeed_thenFollowerCanGetPostFromFeed(){
        // given
        CreatePostRequestDto dto = new CreatePostRequestDto(2L,"user 1 can get this post", PostPublicationState.PUBLIC);
        Long createdPostId = FeedAcceptanceSteps.requestCreatePost(dto);

        // when 팔로워 피드를 요청
        List<GetPostContentResponseDto> result = requestFeed(1L);

        // then
        assertEquals(1, result.size());
        assertEquals(createdPostId, result.get(0).getId());

    }

}

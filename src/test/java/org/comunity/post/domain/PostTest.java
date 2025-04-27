package org.comunity.post.domain;

import org.comunity.post.domain.content.PostContent;
import org.comunity.user.domain.User;
import org.comunity.user.domain.UserInfo;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PostTest {
    private final UserInfo info = new UserInfo("name", "url");
    private final User user = new User(1L, info);
    private final User otherUser = new User(2L, info);

    private final Post post = new Post(1l, user, new PostContent("content"));

    @Test
    void givenPostCreated_whenLike_thenIncreaseLikeCount() {
        // when
        post.like(otherUser);

        // then
        assertEquals(1, post.getLikeCount());
    }

    @Test
    void givenPostCreated_whenLikeByAuthor_thenThrowException() {
        // when, then
        assertThrows(IllegalArgumentException.class, () -> post.like(user));
    }

    @Test
    void givenPostCreatedAndLike_whenUnlike_thenDecreaseLikeCount() {
        // given
        post.like(otherUser);

        // when
        post.unlike();

        // then
        assertEquals(0, post.getLikeCount());
    }

    @Test
    void givenPostCreated_whenUnlike_thenLikeCountShouldBe0() {
        // when
        post.unlike();

        // then
        assertEquals(0, post.getLikeCount());
    }

    @Test
    void givenPostCreated_whenUpdateContent_thenContentShouldBeUpdated() {
        // given
        String newPostContent = "new content";

        // when
        post.updateContent(user, newPostContent, PostPublicationState.PUBLIC);

        // then
        assertEquals(newPostContent, post.getContent());
    }

    @Test
    void givenPostCreated_whenUpdateContentByOtherUser_thenThrowException() {
        // when, then
        assertThrows(IllegalArgumentException.class, () -> post.updateContent(otherUser, "new content", PostPublicationState.PUBLIC));
    }
}

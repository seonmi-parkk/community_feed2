package org.comunity.post.domain.comment;

import org.comunity.post.domain.Post;
import org.comunity.post.domain.content.CommentContent;
import org.comunity.post.domain.content.PostContent;
import org.comunity.user.domain.User;
import org.comunity.user.domain.UserInfo;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommentTest {
    private final UserInfo info = new UserInfo("name", "url");
    private final User user = new User(1L, info);
    private final User otherUser = new User(2L, info);

    private final Post post = new Post(1l, user, new PostContent("content"));
    private final Comment comment = new Comment(1L, post, user, new CommentContent("content"));

    @Test
    void givenCommentCreated_whenLike_thenIncreaseLikeCount() {
        // when
        comment.like(otherUser);

        // then
        assertEquals(1, comment.getLikeCount());
    }

    @Test
    void gievenCommentCreated_whenLikeByAuthor_thenThrowException() {
        // when, then
        assertThrows(IllegalArgumentException.class, () -> comment.like(user));
    }

    @Test
    void givenCommentCreated_whenUnlike_thenDecreaseLikeCount() {
        // given
        comment.like(otherUser);

        // when
        comment.unlike();

        // then
        assertEquals(0, comment.getLikeCount());
    }

    @Test
    void givenCommentCreated_whenUnlike_thenLikeCountShouldBe0() {
        // when
        comment.unlike();

        // then
        assertEquals(0, comment.getLikeCount());
    }

    @Test
    void givenComment_whenUpdateContent_thenShouldBeUpdated() {
        // given
        String newCommentContent = "new content";

        // when
        comment.updateComment(user, newCommentContent);

        // then
        assertEquals(newCommentContent, comment.getContent());
    }

    @Test
    void givenComment_whenUpdateContentOver100_thenThrowException() {
        // given
        String newCommentContent = "a".repeat(101);

        // when, then
        assertThrows(IllegalArgumentException.class, () -> comment.updateComment(user, newCommentContent));
    }
}

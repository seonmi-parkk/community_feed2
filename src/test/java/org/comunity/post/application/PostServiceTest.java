package org.comunity.post.application;

import org.comunity.post.application.dto.LikeRequestDto;
import org.comunity.post.application.dto.UpdatePostRequestDto;
import org.comunity.post.domain.Post;
import org.comunity.post.domain.PostPublicationState;
import org.comunity.post.domain.content.Content;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PostServiceTest extends PostApplicationTestTemplete{

    @Test
    void givenPostRequestDto_whenCreate_thenReturnPost() {
        // when
        Post savedPost = postService.createPost(postRequestDto);

        // then
        Post post = postService.getPost(savedPost.getId());
        assertEquals(savedPost, post);
    }

    @Test
    void givenCreatePost_whenUpdate_thenReturnUpdatedPost() {
        // given
        Post savedPost = postService.createPost(postRequestDto);

        // when
        UpdatePostRequestDto updateDto = new UpdatePostRequestDto(user.getId(), "updated-content", PostPublicationState.PRIVATE);
        Post updatedPost = postService.updatePost(savedPost.getId(), updateDto);

        // then
        Content content = updatedPost.getContentObject();
        assertEquals("updated-content", content.getContentText());
        assertEquals(PostPublicationState.PRIVATE, updatedPost.getState());
    }

    @Test
    void givenCreatedPost_whenLiked_thenReturnPostWithLike() {
        // given
        Post savedPost = postService.createPost(postRequestDto);

        // when
        LikeRequestDto likeRequestDto = new LikeRequestDto(savedPost.getId(), otherUser.getId());
        postService.likePost(likeRequestDto);

        // then
        assertEquals(1, savedPost.getLikeCount());
    }

    @Test
    void givenCreatedPost_whenLikedTwice_thenReturnPostWithLike() {
        // given
        Post savedPost = postService.createPost(postRequestDto);

        // when
        LikeRequestDto likeRequestDto = new LikeRequestDto(savedPost.getId(), otherUser.getId());
        postService.likePost(likeRequestDto);
        postService.likePost(likeRequestDto);

        // then
        assertEquals(1, savedPost.getLikeCount());
    }

    @Test
    void givenCreatedPostLiked_whenUnliked_thenReturnPostWithoutLike() {
        // given
        Post savedPost = postService.createPost(postRequestDto);
        LikeRequestDto likeRequestDto = new LikeRequestDto(savedPost.getId(), otherUser.getId());
        postService.likePost(likeRequestDto);

        // when
        postService.unlikePost(likeRequestDto);

        // then
        assertEquals(0, savedPost.getLikeCount());
    }

    @Test
    void givenCreatedPost_whenUnliked_thenReturnPostWithoutLike() {
        // given
        Post savedPost = postService.createPost(postRequestDto);

        // when
        LikeRequestDto likeRequestDto = new LikeRequestDto(savedPost.getId(), otherUser.getId());
        postService.unlikePost(likeRequestDto);

        // then
        assertEquals(0, savedPost.getLikeCount());
    }



}

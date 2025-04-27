package org.comunity.post.ui;

import lombok.RequiredArgsConstructor;
import org.comunity.common.ui.Response;
import org.comunity.post.application.PostService;
import org.comunity.post.application.dto.CreatePostRequestDto;
import org.comunity.post.application.dto.LikeRequestDto;
import org.comunity.post.application.dto.UpdatePostRequestDto;
import org.comunity.post.domain.Post;
import org.comunity.user.application.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {

    private final UserService userService;
    private final PostService postService;

    @PostMapping
    public Response<Long> createPost (@RequestBody CreatePostRequestDto dto){
        Post post = postService.createPost(dto);
        return Response.ok(post.getId());
    }

    @PatchMapping("/{postId}")
    public Response<Long> updatePost (@PathVariable(name = "postId") Long postId, @RequestBody UpdatePostRequestDto dto){
        Post post = postService.updatePost(postId, dto);
        return Response.ok(post.getId());
    }

    @PostMapping("/like")
    public Response<Void> LikePost(@RequestBody LikeRequestDto dto){
        postService.likePost(dto);
        return Response.ok(null);
    }

    @PostMapping("/unlike")
    public Response<Void> UnlikePost(@RequestBody LikeRequestDto dto){
        postService.unlikePost(dto);
        return Response.ok(null);
    }
}


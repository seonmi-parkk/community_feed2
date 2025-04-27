package org.comunity.post.application;

import org.comunity.fake.FakeObjectFactory;
import org.comunity.post.application.dto.CreateCommentRequestDto;
import org.comunity.post.application.dto.CreatePostRequestDto;
import org.comunity.post.domain.Post;
import org.comunity.post.domain.PostPublicationState;
import org.comunity.user.application.UserService;
import org.comunity.user.application.dto.CreateUserRequestDto;
import org.comunity.user.domain.User;

public class PostApplicationTestTemplete {
    // postService와 CommentService가 겹치는 부분이 많아서 유지보수가 쉽도록 제작한 템플릿
    final UserService userService = FakeObjectFactory.getUserService();
    final PostService postService = FakeObjectFactory.getPostService();
    final CommentService commentService = FakeObjectFactory.getCommentService();

    final User user = userService.createUser(new CreateUserRequestDto("user1", null));
    final User otherUser = userService.createUser(new CreateUserRequestDto("user1", null));

    CreatePostRequestDto postRequestDto = new CreatePostRequestDto(user.getId(), "this is test content", PostPublicationState.PUBLIC);
    final Post post = postService.createPost(postRequestDto);

    final String commentContentText = "this is test comment";
    CreateCommentRequestDto commentRequestDto = new CreateCommentRequestDto(post.getId(), user.getId(), "this is test comment");

}

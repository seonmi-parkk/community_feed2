package org.comunity.post.application;

import org.comunity.post.application.dto.CreateCommentRequestDto;
import org.comunity.post.application.dto.LikeRequestDto;
import org.comunity.post.application.dto.UpdateCommentRequestDto;
import org.comunity.post.application.dto.UpdatePostRequestDto;
import org.comunity.post.application.interfaces.CommentRepository;
import org.comunity.post.application.interfaces.LikeRepository;
import org.comunity.post.domain.Post;
import org.comunity.post.domain.comment.Comment;
import org.comunity.user.application.UserService;
import org.comunity.user.domain.User;
import org.springframework.stereotype.Service;

@Service
public class CommentService {
    private final UserService userService;
    private final PostService postService;
    private final CommentRepository commentRepository;
    private final LikeRepository likeRepository;

    public CommentService(UserService userService, PostService postService, CommentRepository commentRepository, LikeRepository likeRepository) {
        this.userService = userService;
        this.postService = postService;
        this.commentRepository = commentRepository;
        this.likeRepository = likeRepository;
    }

    public Comment getComment(Long id){
        return commentRepository.findById(id);
    }

    public Comment createComment(CreateCommentRequestDto dto) {
        Post post = postService.getPost(dto.postId());
        User user = userService.getUser(dto.userId());

        Comment comment = Comment.createComment(post, user, dto.content());
        return commentRepository.save(comment);
    }

    public Comment updateComment(Long commentId, UpdateCommentRequestDto dto) {
        Comment comment = getComment(commentId);
        User user = userService.getUser(dto.userId());

        comment.updateComment(user, dto.content());
        return commentRepository.save(comment);
    }

    public void likeComment(LikeRequestDto dto) {
        Comment comment = getComment(dto.targetId());
        User user = userService.getUser(dto.userId());

        if(likeRepository.checkLike(comment, user)) {
            return;
        }

        comment.like(user);
        likeRepository.like(comment, user);
    }

    public void unlikeComment(LikeRequestDto dto) {
        Comment comment = getComment(dto.targetId());
        User user = userService.getUser(dto.userId());

        if(likeRepository.checkLike(comment, user)) {
            comment.unlike();
            likeRepository.unlike(comment, user);
        }
    }
}

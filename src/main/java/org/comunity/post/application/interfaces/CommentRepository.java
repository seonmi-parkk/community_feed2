package org.comunity.post.application.interfaces;

import org.comunity.post.domain.comment.Comment;

import java.util.Optional;

public interface CommentRepository {
    Comment save(Comment comment);
    Comment findById(Long id);
}

package org.comunity.post.repository;

import org.comunity.post.application.interfaces.CommentRepository;
import org.comunity.post.domain.comment.Comment;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class FakeCommentRepository implements CommentRepository {

    private final Map<Long, Comment> store = new HashMap<>();

    @Override
    public Comment save(Comment comment) {
        if (comment.getId() != null) {
            store.put(comment.getId(), comment);
            return comment;
        } else {
            long id = store.size() + 1;
            Comment newComment = new Comment(id, comment.getPost(), comment.getAuthor(), comment.getContentObject());
            store.put(id, newComment);
            return newComment;
        }
    }

    @Override
    public Comment findById(Long id) {
        return store.get(id);
    }
}

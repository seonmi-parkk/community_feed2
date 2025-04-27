package org.comunity.post.application.interfaces;

import org.comunity.post.domain.Post;

public interface PostRepository {

    Post save(Post post);

    Post findById(Long id);

}

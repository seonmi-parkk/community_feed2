package org.comunity.post.domain.comment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.comunity.common.domain.PositiveIntegerCounter;
import org.comunity.post.application.interfaces.LikeRepository;
import org.comunity.post.domain.Post;
import org.comunity.post.domain.content.CommentContent;
import org.comunity.post.domain.content.Content;
import org.comunity.user.domain.User;

@Getter
@Builder
@AllArgsConstructor
public class Comment {

    private final Long id;
    private final Post post;
    private final User author;
    private final Content content;
    private final PositiveIntegerCounter likeCount;

    public static Comment createComment(Post post, User author, String content){
        return new Comment(null, post, author, new CommentContent(content));
    }

    public Comment(Long id, Post post, User author, Content content) {
        if (author == null) {
            throw new IllegalArgumentException();
        }
        if (post == null) {
            throw new IllegalArgumentException();
        }
        if (content == null) {
            throw new IllegalArgumentException();
        }

        this.id = id;
        this.post = post;
        this.author = author;
        this.content = content;
        this.likeCount = new PositiveIntegerCounter();
    }

    public void like(User user) {
        if(this.author.equals(user)) {
            throw new IllegalArgumentException();
        }
        likeCount.increase();
    }

    public void unlike() {
        this.likeCount.decrease();
    }

    public void updateComment(User user, String updatedContent) {
        if(!this.author.equals(user)) {
            throw new IllegalArgumentException();
        }

        this.content.updateContent(updatedContent);
    }

    public int getLikeCount() {
        return likeCount.getCount();
    }

    public String getContent() {
        return content.getContentText();
    }

    public Content getContentObject() {
        return content;
    }
}

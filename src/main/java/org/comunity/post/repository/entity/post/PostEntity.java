package org.comunity.post.repository.entity.post;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.comunity.common.domain.PositiveIntegerCounter;
import org.comunity.common.repository.entity.TimeBaseEntity;
import org.comunity.post.domain.Post;
import org.comunity.post.domain.PostPublicationState;
import org.comunity.post.domain.content.PostContent;
import org.comunity.user.repository.entity.UserEntity;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "community_post")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class PostEntity extends TimeBaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // user가 1명 게시글이 여러개인 양방향 맵핑
    // @ManyToOne는 FetchType.EAGER가 default값
    // 해당 필드(author)를 사용하지 않아도 db에서 post 데이터를 불러올 때
    // 같이 author_id에 대한 데이터를 가져옴
    // EX) select * from post join user u on p.user_id = u.id;
    // foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT) => ddl 생성시 foreign key 생성제한
    // 실무에서 foreignKey를 잘사용하지 않음.
        // 원치않는 인덱스를 걸기도하고 제약조건 때문에 데이터 수정이 어려워짐.
    @ManyToOne
    @JoinColumn(name="author_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private UserEntity author;

    private String content;

    // enum객체 <-> string 변경을 쉽게 해주는 convert 어노테이션 추가
    // & 변환해주는 convert클래스(PostPublicationStateConverter) 추가
    @Convert(converter = PostPublicationStateConverter.class)
    private PostPublicationState state;

    private Integer likeCount;

    @ColumnDefault("0") // db에서 default값을 0으로 자동 설정
    private int commentCount;

    public PostEntity(Post post) {
        this.id = post.getId();
        this.author = new UserEntity(post.getAuthor());
        this.content = post.getContent();
        this.state = post.getState();
        this.likeCount = post.getLikeCount();
    }

    public Post toPost() {
        return Post.builder()
                .id(id)
                .author(author.toUser())
                .content(new PostContent(content))
                .state(state)
                .likeCount(new PositiveIntegerCounter(likeCount))
                .build();
    }
}

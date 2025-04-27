package org.comunity.post.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.comunity.common.domain.PositiveIntegerCounter;
import org.comunity.post.domain.content.Content;
import org.comunity.post.domain.content.PostContent;
import org.comunity.user.domain.User;

@Getter
@Builder
@AllArgsConstructor
public class Post {
    private final Long id; // postId

    // 글쓴이의 User 객체를 가지냐 Long타입으로 아이디를 가지고 있느냐 선택
    // User 객체를 참조하고 있으면
        // 유저객체에 대한 기능이 생긴다면 메서드로 바로 사용가능한 것이 좋고,
        // 유저라는 객체를 참조하고 있다는 가독성이 좋음.
    // 단점은 테스트 셋팅할때 번거로움.
    // Long으로 Id를 주입받을 때 단점은
        // 행동기반으로 생각해보면 객체지향에 가까운 방법이 아님.
    // 우리는 유저의 객체를 확인하고 저장하고 있기 때문에 객체를 주입해주는데 어려움이 없기 때문에 User객체를 주입받는 방향으로 진행
    //private final Long authorId;
    private final User author;
    private final Content content;
    private final PositiveIntegerCounter likeCount;
    // 노출상태 enum값
    private PostPublicationState state;

    // 정적생성자(정적 팩토리 메서드) 사용 => 아래 일반 생성자와 똑같은 기능 수행
    // 정적 팩토리 메서드의 장점
        // 메서드명을 통해서 어떤 생성자를 나타내는지 알수 있음.(가독성)

    // dto로 인자를 넘겨주는 것이 아닌 여러가지 데이터를 파라미터로 넘겨주면 복잡하고 유지보수가 힘들지 않나?
        // 2가지 방법이 있음.
        // 1. 빌더패턴 적용
            // 롬복 사용해서 생성자들을 체이닝형태로 가독성있게 작석
        // 2. 객체를 더 작은 객체로 나누어서 생성자에 한두개 이상의 상태값을 가지지 못하도록 하는 방법
    public static Post createPost(Long id, User author, String content, PostPublicationState state) {
        return new Post(id, author, new PostContent(content), state);
    }
    public static Post createDefaultPost(Long id, User author, String content) {
        return new Post(id, author, new PostContent(content), PostPublicationState.PUBLIC);
    }

    public Post(Long id, User author, Content content) {
        this(id, author, content, PostPublicationState.PUBLIC);
    }
    public Post(Long id, User author, Content content, PostPublicationState state) {
        if(author == null){
            throw new IllegalArgumentException("");
        }

        this.id = id;
        //this.authorId = author.getId();
        this.author = author;
        this.content = content;
        this.likeCount = new PositiveIntegerCounter();
        this.state = state;
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

    public void updateContent(User user, String updateContent, PostPublicationState state) {
        if(!this.author.equals(user)) {
            throw new IllegalArgumentException();
        }

        this.state = state;
        this.content.updateContent(updateContent);
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

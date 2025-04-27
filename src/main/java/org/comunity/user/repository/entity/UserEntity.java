package org.comunity.user.repository.entity;

import jakarta.persistence.*;
import lombok.*;
import org.comunity.common.domain.PositiveIntegerCounter;
import org.comunity.common.repository.entity.TimeBaseEntity;
import org.comunity.user.domain.User;
import org.comunity.user.domain.UserInfo;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "community_user")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@DynamicUpdate // 변경된 부분만 update 쿼리문을 날림
public class UserEntity extends TimeBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String profileImage;
    private Integer followerCount;
    private Integer followingCount;

    // @OneToMany는 FetchType.LAZY이 default값
    //FetchType.LAZY => user에 대한 정보를 db에서 가져올 때 posts 필드가 쓰이기 전까지는
    // 해당 필드(posts=유저가 작성한 글)에 대한 데이터들은 아직 db에서 가져오지 않음.
    // ex) select * from user where id = 1;
    // 후에 이 user에서 posts를 가져오는 쿼리문이 작성된다면 select * from post where author_id = 1; 이라는 쿼리 작성됨.
    // 만약 여기서 @OneToMany(fetch = FetchType.EAGER)로 설정한다면
    // user가 작성한 글이 천만개일경우 천만개를 자바객체로 만들어서 필드로 가지고 있게 됨 -> 메모리 부족으로 장애로 이어질 수 있음.
    // 그래서 실무에서는 @OneToMany를 잘 사용하지 않음.
    // -> repository를 이용해서 data불러오는 방법을 선택
    // 또한 페이징 처리와 어떤 인텍스를 타야할지 정확히 설정을 해줘야 하기 때문에 oneToMany보다는 단방향 맵핑을 선택하는 경우가 많음.
    // 그리고 만약 @OneToMany가 걸려있는 userEntity에서 post를 조회하는 getPost()를 반복문으로 수행한다면 반복문 안에서 쿼리문을 여러번 수행하게되는 n+1문제가 발생.
    // 따라서 되도록이면 한정된 데이터를 가지고 있거나 필수적으로 함께 조회되어야 하는 데이터가 아니라면 oneToMany 사용 지양.

    //@OneToMany
    //private List<PostEntity> posts;

    public UserEntity(User user){
        this.id = user.getId();
        this.name = user.getName();
        this.profileImage = user.getProfileImage();
        this.followerCount = user.followerCount();
        this.followingCount = user.followingCount();
    }

    public User toUser() {
        return User.builder()
                .id(id)
                .userInfo(new UserInfo(name, profileImage))
                .followerCounter(new PositiveIntegerCounter(followerCount))
                .followingCounter(new PositiveIntegerCounter(followingCount))
                .build();

    }

}

package org.comunity.user.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.comunity.common.domain.PositiveIntegerCounter;

import java.util.Objects;
@Getter
@Builder
@AllArgsConstructor
public class User {
    private Long id;
    private UserInfo userInfo;
    // 팔로잉 팔로워 카운터는 로직이 동일
    // 매번 증가, 감소 할때마다 동일한 검증을 하는 것 보다는 vo를 통해 감싸서 안전하게 처리
    private PositiveIntegerCounter followingCounter;
    private PositiveIntegerCounter followerCounter;

    public User(Long id, UserInfo userInfo) {
        if (userInfo == null) {
            throw new NullPointerException();
        }
        this.id = id;
        this.userInfo = userInfo;
        this.followingCounter = new PositiveIntegerCounter();
        this.followerCounter = new PositiveIntegerCounter();
    }

    public void follow(User targetUser) {
        if(targetUser.equals(this)) {
            throw new IllegalArgumentException();
        }

        followingCounter.increase();
        // 한줄에 두개의 점 (디미터의 법칙 - 자신의 소유 객체와만 대화하라 => 캡슐화가 깨어지지 않도록)
        //targetUser.followerCounter.increase();
        targetUser.increaseFollowerCounter();
    }

    public void unfollow(User targetUser) {
        if(this.equals(targetUser)) {
            throw new IllegalArgumentException();
        }

        followingCounter.decrease();

        //targetUser.followerCounter.decrease();
        targetUser.decreaseFollowerCounter();
    }

    private void increaseFollowerCounter(){
        followerCounter.increase();
    }

    private void decreaseFollowerCounter(){
        followerCounter.decrease();
    }

    // id로 객체를 구분해야하기 때문에 => equals랑 hashcode메서드 오버라이딩 해야함.
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    public int followerCount() {
        return followerCounter.getCount();
    }

    public int followingCount() {
        return followingCounter.getCount();
    }


    public String getProfileImage() {
        return userInfo.getProfileImageUrl();
    }

    public String getName() {
        return userInfo.getName();
    }

}

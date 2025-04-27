package org.comunity.user.application.interfaces;

import org.comunity.user.domain.User;

public interface UserRelationRepository {

    boolean isAlreadyFollow(User user, User targetUser);
    void save(User user, User targetUser);
    void delete(User user, User targetUser);
    // userid를 넘기지않고 user객체 전체를 넘기는 이유
        // 변경이 있는 도메인 객체 전체를 넘기면 User의 count도 같이 업데이트 되어야 하기때문
        // transaction단위로 변경이 일어나야 하는 것들은 같이 메서드 하나로 묶어주면 유지보수가 쉬워지는 장점이 있음.
        // 만약 userid만 넘겼다면 select를 한번 더 하거나 내부적으로 유저들의 카운트를 한번 더 가져와야 하거나
        // 아니면 메서드를 넘겨줘야하는 추가적인 유저 정보들이 있다먼 매번 이런 인터페이스들의 변경이 일어날것이다.
        // 유저 전체를 파라미터로 넘겨줌으로써 도메인 데이터를 넘겨주게되면 유저 내부만 변경을 해도 인터페이스에는 변화가 없다.

}

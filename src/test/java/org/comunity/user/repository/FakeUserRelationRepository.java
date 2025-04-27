package org.comunity.user.repository;

import org.comunity.user.application.interfaces.UserRelationRepository;
import org.comunity.user.domain.User;

import java.util.HashSet;
import java.util.Set;

public class FakeUserRelationRepository implements UserRelationRepository {
    // 기존과 다르게 map을 사용하기 어려움. (한쌍의 유저를 유효성을 확인해야하기 때문)
    // 이런 경우 테스트내부에서만 사용되는 record객체 사용해서 페이크 객체 만들기
        // equals and hashcode는 record 내에 구현이 되어있음. => 같은 객체인지 확인가능

    // 가짜 테스트객체를 만들어서 테스트하는 것의 이점?
        // 충분한 일정, 변경이 자주 있는 프로젝트 처음 설계시 유용
        // 외부적인 것들이 결정나지 않았을때 먼저 구현체 만들고 테스트 만든경우 => 만든 테스트 케이스 사용 불가
        // 가짜 객체를 위한 h2 설정 정도 폼이 듦.
        // 반면 테스트 객체는 빠르게 만들고 유연하게 설정가능.
    private final Set<Relation> store = new HashSet<>();

    @Override
    public boolean isAlreadyFollow(User user, User targetUser) {
        return store.contains(new Relation(user.getId(), targetUser.getId()));
    }

    @Override
    public void save(User user, User targetUser) {
        store.add(new Relation(user.getId(), targetUser.getId()));
    }

    @Override
    public void delete(User user, User targetUser) {
        store.remove(new Relation(user.getId(), targetUser.getId()));
    }
}

record Relation(Long userId, Long targetUserId) {};

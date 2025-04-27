package org.comunity.user.repository;

import org.comunity.user.application.interfaces.UserRepository;
import org.comunity.user.domain.User;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class FakeUserRepository implements UserRepository {
    // 수행과정이 rdb랑 똑같도록 구성
    private final Map<Long, User> store = new HashMap<>();

    @Override
    public User save(User user) {
        if(user.getId() != null) {
            // 업데이트
            store.put(user.getId(), user);
        }
        Long id = store.size() + 1L;
        User newUser = new User(id, user.getUserInfo());
        store.put(id, newUser);
        return newUser;
    }

    @Override
    public User findById(Long id) {
        return store.get(id);
    }
}

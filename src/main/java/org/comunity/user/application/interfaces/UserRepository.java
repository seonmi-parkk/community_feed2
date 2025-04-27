package org.comunity.user.application.interfaces;

import org.comunity.user.domain.User;

import java.util.Optional;

public interface UserRepository {

    User save(User user);
    User findById(Long id);
}

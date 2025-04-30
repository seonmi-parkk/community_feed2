package org.comunity.auth.repository.jpa;

import org.comunity.auth.domain.UserAuth;
import org.comunity.auth.repository.entity.UserAuthEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaUserAuthRepostiory extends JpaRepository<UserAuthEntity, Long> {
}

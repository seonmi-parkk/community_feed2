package org.comunity.user.repository.jpa;

import org.comunity.user.repository.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

//JpaRepository<가지고 와야하는 entity, entity의 id 타입>
public interface JpaUserRepository extends JpaRepository<UserEntity, Long> {
}

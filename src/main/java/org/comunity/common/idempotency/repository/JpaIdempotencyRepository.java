package org.comunity.common.idempotency.repository;

import org.comunity.common.idempotency.Idempotency;
import org.comunity.common.idempotency.repository.entity.IdempotencyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaIdempotencyRepository extends JpaRepository<IdempotencyEntity, Long> {
    Optional<IdempotencyEntity> findByIdempotencyKey(String key);
}

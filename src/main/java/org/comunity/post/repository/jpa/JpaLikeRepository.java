package org.comunity.post.repository.jpa;

import org.comunity.post.repository.entity.like.LikeEntity;
import org.comunity.post.repository.entity.like.LikeIdEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaLikeRepository extends JpaRepository<LikeEntity, LikeIdEntity> {
}

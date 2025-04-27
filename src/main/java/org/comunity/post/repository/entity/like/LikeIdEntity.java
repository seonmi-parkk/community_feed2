package org.comunity.post.repository.entity.like;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Embeddable
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class LikeIdEntity {
    // post or comment의 id
    private Long targetId;
    private Long userId;
    private String targetType; // post or comment 인지 구분
}

package org.comunity.common.repository.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

// EntityListeners - hibernate가 db에 어떤 행위를 하는 시점에 콜백 함수를 제공
// AuditingEntityListener.class - 생성, 수정시 자동으로 칼럼에 데이터를 채워넣어줌.
// AuditingEntityListener 사용하려면 APPlication에 @EnableJpaAuditing 어노테이션을 붙여야함
@EntityListeners(AuditingEntityListener.class)
// MappedSuperclass - 공통 맵핑 정보가 필요할 때 속성만 상속받아서 사용할 수 있게 만들어주는 어노테이션
@MappedSuperclass
@Getter
public class TimeBaseEntity {
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime regDt;

    @LastModifiedDate
    private LocalDateTime updDt;
}

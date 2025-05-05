package org.comunity.common.idempotency.repository.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.comunity.common.idempotency.Idempotency;
import org.comunity.common.ui.Response;
import org.comunity.common.utils.ResponseObjectMapper;

@Entity
@Table(name = "community_idempotency")
@NoArgsConstructor
@AllArgsConstructor
public class IdempotencyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String idempotencyKey;

    // 빠른 구현을 위해서 json 형태가 아닌 String으로 저장
    @Getter
    @Column(nullable = false)
    private String response;

    public IdempotencyEntity(Idempotency idempotency) {
        this.idempotencyKey = idempotency.getKey();
        // 객체를 String으로 변환하는 ObjectMapper 추가
        this.response = ResponseObjectMapper.toStringResponse(idempotency.getResponse());
    }

    public Idempotency toIdempotency() {
        return new Idempotency(this.idempotencyKey, ResponseObjectMapper.toResponseObject(response));
    }

}

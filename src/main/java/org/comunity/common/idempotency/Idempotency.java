package org.comunity.common.idempotency;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.comunity.common.ui.Response;

// 응답값 객체를 키와함께 가지고 있는 객체
@Getter
@AllArgsConstructor
public class Idempotency {
    private final String key;
    private final Response<?> response;
}

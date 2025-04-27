package org.comunity.common.ui;

import org.comunity.common.domain.exception.ErrorCode;

// 응답값으로 어떤것들이 올지 모르기 때문에 제네릭타입으로 처리
// code : 클라이언트에서 어떤 응답인지 확인할 수 있도록
// value : 응답값
public record Response<T>(Integer code, String message, T value) {

    public static <T> Response<T> ok(T value) {
        return new Response<>(0, "OK", value);
    }

    // exception별로 손쉽게 핸들링할 수 있도록 global exception handler를 추가해서
    // 항상 같은 형태의 response가 내려갈 수 있도록 함.
    // 에러코드 enum 추가(errorCode)

    // 에러코드를 받아서 바로 return
    public static <T> Response<T> error(ErrorCode errorCode) {
        return new Response<>(errorCode.getCode(), errorCode.getMessage(), null);
    }

}

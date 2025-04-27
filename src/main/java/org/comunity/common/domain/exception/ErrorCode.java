package org.comunity.common.domain.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {

    INVALID_INPUT_VALUE(400, "Invalid Input Value"),
    NOT_FOUND(404, "Not Found Data"),
    INTERNAL_ERROR(500, "Unexpected Error");

    private final Integer code;
    private final String message;

    ErrorCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}

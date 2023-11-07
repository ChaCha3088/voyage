package com.ssafy.voyage.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
public class Response {
    private String cause;
    private String message;

    @Builder
    protected Response(String cause, String message) {
        this.cause = cause;
        this.message = message;
    }
}

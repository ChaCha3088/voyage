package com.ssafy.voyage.exception;

import lombok.Getter;

@Getter
public class MemberFormValidationException extends RuntimeException {
    private final String reason;
    private final String message;

    public MemberFormValidationException(String reason, String message) {
        super();
        this.reason = reason;
        this.message = message;
    }
}

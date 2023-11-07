package com.ssafy.voyage.exception;

import lombok.Getter;

@Getter
public class MemberCreationValidationException extends Exception {
    private final String reason;
    private final String message;

    public MemberCreationValidationException(String reason, String message) {
        super();
        this.reason = reason;
        this.message = message;
    }
}

package com.ssafy.voyage.message.cause;

import lombok.Getter;

@Getter
public enum Causes {
    REQUEST_VALIDATION("Request Validation");

    private final String message;

    private Causes(String message) {
        this.message = message;
    }
}

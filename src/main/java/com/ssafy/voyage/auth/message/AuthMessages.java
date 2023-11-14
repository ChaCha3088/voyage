package com.ssafy.voyage.auth.message;

import lombok.Getter;

@Getter
public enum AuthMessages {
    SIGN_IN("로그인 ");

    private final String message;

    private AuthMessages(String message) {
        this.message = message;
    }
}

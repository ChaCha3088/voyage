package com.ssafy.voyage.auth.message;

import lombok.Getter;

@Getter
public enum AuthMessages {
    SIGN_UP("회원가입 "),
    SIGN_IN("로그인 "),
    SIGN_OUT("로그아웃 "),
    AUTHENTICATION("인증 "),
    AUTHORIZATION("인가 ");

    private final String message;

    private AuthMessages(String message) {
        this.message = message;
    }
}

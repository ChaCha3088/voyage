package com.ssafy.voyage.message.cause;

import lombok.Getter;

@Getter
public enum MemberCause {
    MEMBER("member"),
    NAME("name"),
    EMAIL("email"),
    PASSWORD("password"),
    VERIFICATION("verification"),
    FORM("form");

    private final String message;

    private MemberCause(String message) {
        this.message = message;
    }
}

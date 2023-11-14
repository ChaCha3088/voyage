package com.ssafy.voyage.auth.message;

import lombok.Getter;

@Getter
public enum JwtCauses {
    JWT("JWT");

    private final String message;

    private JwtCauses(String message) {
        this.message = message;
    }
}

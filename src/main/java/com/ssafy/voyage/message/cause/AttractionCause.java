package com.ssafy.voyage.message.cause;

import lombok.Getter;

@Getter
public enum AttractionCause {
    ATTRACTION("attraction");

    private final String message;

    private AttractionCause(String message) {
        this.message = message;
    }
}

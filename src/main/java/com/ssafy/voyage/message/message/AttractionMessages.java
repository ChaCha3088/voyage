package com.ssafy.voyage.message.message;

import lombok.Getter;

@Getter
public enum AttractionMessages {
    ATTRACTION("관광지 ");

    private final String message;

    private AttractionMessages(String message) {
        this.message = message;
    }
}

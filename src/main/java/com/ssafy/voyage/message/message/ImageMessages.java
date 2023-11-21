package com.ssafy.voyage.message.message;

import lombok.Getter;

@Getter
public enum ImageMessages {
    MULTIPART_FILE("Multipart 파일 "),
    CONVERT("변환 "),
    EXTENSION("확장자 "),
    COUNT("개수 "),
    SIZE("크기 "),;

    private final String message;

    private ImageMessages(String message) {
        this.message = message;
    }
}

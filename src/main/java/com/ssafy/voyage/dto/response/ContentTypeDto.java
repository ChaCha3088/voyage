package com.ssafy.voyage.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
public class ContentTypeDto {
    private final int contentTypeId;
    private final String contentType;

    @Builder
    protected ContentTypeDto(int contentTypeId, String contentType) {
        this.contentTypeId = contentTypeId;
        this.contentType = contentType;
    }
}

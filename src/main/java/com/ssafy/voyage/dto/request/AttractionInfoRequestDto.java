package com.ssafy.voyage.dto.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
public class AttractionInfoRequestDto {
    @NotNull
    private final long lastId;
    @NotNull
    private final int sidoCode;
    @NotNull
    private final int contentTypeId;
    @NotNull
    private final String title;
    @NotNull
    private final int pageSize;

    @Builder
    protected AttractionInfoRequestDto(long lastId, int sidoCode, int contentTypeId, String title, int pageSize) {
        this.lastId = lastId;
        this.sidoCode = sidoCode;
        this.contentTypeId = contentTypeId;
        this.title = title;
        this.pageSize = pageSize;
    }
}

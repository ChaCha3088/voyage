package com.ssafy.voyage.entity;

import com.ssafy.voyage.dto.response.ContentTypeDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity(name = "content_type")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ContentType {
    @Id
    private int contentTypeId;

    @NotBlank
    private String contentType;

    @NotNull
    @OneToMany(mappedBy = "contentTypeId")
    private List<AttractionInfo> attractionInfos = new ArrayList<>();

    @Builder
    protected ContentType(int contentTypeId, String contentType) {
        this.contentTypeId = contentTypeId;
        this.contentType = contentType;
    }

    // -- Dto -- //
    public ContentTypeDto toContentTypeDto() {
        return ContentTypeDto.builder()
            .contentTypeId(contentTypeId)
            .contentType(contentType)
            .build();
    }
}

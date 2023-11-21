package com.ssafy.voyage.dto.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
public class MemberProfileImageUpdateDto {
    @NotNull
    private final MultipartFile multipartFile;

    @Builder
    protected MemberProfileImageUpdateDto(MultipartFile multipartFile) {
        this.multipartFile = multipartFile;
    }
}

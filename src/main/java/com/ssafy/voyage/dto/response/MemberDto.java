package com.ssafy.voyage.dto.response;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class MemberDto {
    @NotBlank
    protected String email;
    @NotBlank
    protected String name;
    protected String profileImageUrl;

    @Builder
    protected MemberDto(String email, String name, String profileImageUrl) {
        this.email = email;
        this.name = name;
        this.profileImageUrl = profileImageUrl;
    }
}

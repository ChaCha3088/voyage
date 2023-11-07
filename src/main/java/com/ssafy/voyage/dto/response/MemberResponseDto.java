package com.ssafy.voyage.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberResponseDto {
    private String email;
    private String name;

    @Builder
    public MemberResponseDto(String email, String name) {
        this.email = email;
        this.name = name;
    }
}

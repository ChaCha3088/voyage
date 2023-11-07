package com.ssafy.voyage.dto.member;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class MemberUpdateDto {
    @NotBlank
    private String password;

    @Builder
    public MemberUpdateDto(String password) {
        this.password = password;
    }
}

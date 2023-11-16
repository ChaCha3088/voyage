package com.ssafy.voyage.dto.member;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
public class MemberUpdateDto {
    @NotBlank
    private String password;
    @NotBlank
    private String passwordAgain;

    @Builder
    public MemberUpdateDto(String password, String passwordAgain) {
        this.password = password;
        this.passwordAgain = passwordAgain;
    }
}

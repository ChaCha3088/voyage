package com.ssafy.voyage.dto.member;


import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
public class MemberCreationDto {
    @NotBlank
    private String email;
    @NotBlank
    private String password;
    @NotBlank
    private String passwordAgain;
    @NotBlank
    private String name;

    @Builder
    protected MemberCreationDto(String email, String password, String passwordAgain, String name) {
        this.email = email;
        this.password = password;
        this.passwordAgain = passwordAgain;
        this.name = name;
    }
}

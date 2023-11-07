package com.ssafy.voyage.auth.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
public class MemberSignUpDto {
    private final String name;
    private final String email;
    private final String password;
    private final String passwordAgain;
    private final String city;
    private final String street;
    private final String zipcode;
}

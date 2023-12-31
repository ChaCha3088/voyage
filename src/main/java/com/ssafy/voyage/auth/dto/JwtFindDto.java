package com.ssafy.voyage.auth.dto;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
public class JwtFindDto {
    @NotNull
    private long id;

    @NotBlank
    private String refreshToken;

    @NotNull
    private LocalDateTime expiredAt;

    @NotNull
    private long memberId;

    @Builder
    protected JwtFindDto(long id, String refreshToken, LocalDateTime expiredAt, long memberId) {
        this.id = id;
        this.refreshToken = refreshToken;
        this.expiredAt = expiredAt;
        this.memberId = memberId;
    }
}

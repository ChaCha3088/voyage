package com.ssafy.voyage.auth.entity;

import com.ssafy.voyage.auth.dto.JwtFindDto;
import com.ssafy.voyage.entity.Member;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

import static javax.persistence.FetchType.LAZY;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RefreshToken {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank
    @Column(unique = true, columnDefinition = "varchar(300)")
    private String refreshToken;

    @NotNull
    private LocalDateTime expiredAt;

    @NotNull
    @ManyToOne(fetch = LAZY)
    private Member member;

    @Builder
    protected RefreshToken(String refreshToken, Member member) {
        this.refreshToken = refreshToken;
        this.expiredAt = LocalDateTime.now().plusDays(7);
        this.member = member;
    }

    //== 비즈니스 로직 ==//
    public void updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
        this.expiredAt = LocalDateTime.now().plusDays(7);
    }

    //== Dto ==//
    public JwtFindDto toJwtFindDto() {
        return JwtFindDto.builder()
                .id(id)
                .refreshToken(refreshToken)
                .expiredAt(expiredAt)
                .memberId(member.getId())
                .build();
    }

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(this.expiredAt);
    }

    @Override
    public String toString() {
        return "RefreshToken{" +
                "id=" + id +
                ", refreshToken='" + refreshToken + '\'' +
                ", expiredAt=" + expiredAt +
                ", member=" + member +
                '}';
    }
}

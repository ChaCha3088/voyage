package com.ssafy.voyage.entity;

import com.ssafy.voyage.auth.entity.RefreshToken;
import com.ssafy.voyage.auth.enumstorage.MemberRole;
import com.ssafy.voyage.dto.member.MemberUpdateDto;
import com.ssafy.voyage.auth.enumstorage.MemberStatus;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true)
    @Email
    private String email;

    @NotBlank
    private String password;

    @NotBlank
    private String name;

    @NotNull
    @Enumerated(EnumType.STRING)
    private MemberRole role = MemberRole.MEMBER;

    @NotNull
    @Enumerated(EnumType.STRING)
    private MemberStatus status = MemberStatus.ACTIVE;

    @NotNull
    @OneToMany(mappedBy = "member")
    private Set<RefreshToken> refreshTokens = new HashSet<>();

    @Builder
    protected Member(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    // 비즈니스 로직
    public void update(MemberUpdateDto memberUpdateDto) {
        this.password = memberUpdateDto.getPassword();
    }
}

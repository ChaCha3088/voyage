package com.ssafy.voyage.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ssafy.voyage.auth.entity.RefreshToken;
import com.ssafy.voyage.auth.enumstorage.MemberRole;
import com.ssafy.voyage.auth.enumstorage.MemberStatus;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.catalina.Store;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MEMBER_ID")
    private long id;

    private String name;

    @NotBlank
    @Column(unique = true)
    private String email;

    private String password;

    @Embedded
    private Address address;

    @NotNull
    @Enumerated(EnumType.STRING)
    private MemberRole role;

    @NotNull
    @Enumerated(EnumType.STRING)
    private MemberStatus status;

    @NotNull
    @OneToMany(mappedBy = "member")
    private Set<RefreshToken> refreshTokens = new HashSet<>();

    @NotNull
    private int logInAttempt = 0;

    @Builder
    protected Member(String name, String email, String password, String city, String street, String zipcode) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.address = Address.builder()
                .city(city)
                .street(street)
                .zipcode(zipcode)
            .build();
        this.role = MemberRole.MEMBER;
        this.status = MemberStatus.ACTIVE;
    }

    @Builder
    protected Member(String email) {
        this.email = email;
        this.role = MemberRole.ADMIN;
        this.status = MemberStatus.ACTIVE;
    }

    //== 비즈니스 로직 ==//
    public void changePassword(String newPassword) {
        this.password = newPassword;
    }

    public void updateMemberAddress(String city, String street, String zipcode) {
        this.address = Address.builder()
                .city(city)
                .street(street)
                .zipcode(zipcode)
                .build();
    }

    public void unlock() {
        if (this.status == MemberStatus.LOCKED) {
            this.status = MemberStatus.ACTIVE;
            this.logInAttempt = 0;
        }
    }

    public MemberStatus countUpLogInAttempt() {
        this.logInAttempt += 1;

        if (this.logInAttempt >= 5) {
            if (this.status != MemberStatus.DELETED) {
                return this.status = MemberStatus.LOCKED;
            }
        }

        return this.status;
    }

    public void resetLogInAttempt() {
        this.logInAttempt = 0;
    }

    public void deleteMember() {
        this.status = MemberStatus.DELETED;
    }

    //== 테스트 로직 ==//
    public void switchRole() {
        if (this.role == MemberRole.ADMIN) {
            this.role = MemberRole.MEMBER;
        } else {
            this.role = MemberRole.ADMIN;
        }
    }

    //==연관관계 메소드==//
    public void addRefreshToken(RefreshToken refreshToken) {
        this.refreshTokens.add(refreshToken);
    }

    public boolean removeRefreshToken(RefreshToken refreshToken) {
        return this.refreshTokens.remove(refreshToken);
    }

    //==DTO==//
}

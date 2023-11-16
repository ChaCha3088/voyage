package com.ssafy.voyage.auth.service;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.ssafy.voyage.auth.exception.NoSuchRefreshTokenInDBException;
import com.ssafy.voyage.auth.validator.AuthValidator;
import com.ssafy.voyage.dto.member.MemberCreationDto;
import com.ssafy.voyage.entity.Member;
import com.ssafy.voyage.exception.MemberCreationValidationException;
import com.ssafy.voyage.exception.NoSuchMemberException;
import com.ssafy.voyage.repository.MemberRepository;
import com.ssafy.voyage.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.ssafy.voyage.message.message.MemberMessages.MEMBER;
import static com.ssafy.voyage.message.message.Messages.*;

@Service
// Transactional 붙이지 마
@RequiredArgsConstructor
public class AuthService {
    private final MemberService memberService;

    private final JwtService jwtService;
    private final AuthValidator authValidator;

    // 회원가입
    @Transactional
    public void signUp(MemberCreationDto memberCreationDto) throws MemberCreationValidationException, DataIntegrityViolationException {
        // 회원 가입 정보 검증
        authValidator.validateMemberCreationDto(memberCreationDto);

        memberService.createMember(memberCreationDto);
    }

<<<<<<< HEAD
    // 로그인
    // Transactional 필요 없음
    public String[] whenMemberSignIn_IssueJwts(String email) throws NoSuchMemberException {
        String[] issuedJwts = jwtService.issueJwts(email);

        return issuedJwts;
    }

=======
>>>>>>> f516500ccb7cee30e4cc4c7da4a3441a682a4b21
    // 로그아웃
    // Transactional 필요 없음
    public void signOut(String refreshToken) {
        // 해당 refreshToken 삭제
        jwtService.deleteRefreshToken(refreshToken);
    }
<<<<<<< HEAD

    // Jwt 재발급
    // Transactional 필요 없음
    public String[] reissueJwts(String refreshToken) throws IllegalArgumentException, JWTVerificationException, NoSuchRefreshTokenInDBException {
        String[] issuedJwts = jwtService.reissueJwts(refreshToken);

        return issuedJwts;
    }
=======
>>>>>>> f516500ccb7cee30e4cc4c7da4a3441a682a4b21
}

package com.ssafy.voyage.auth.service;

import com.ssafy.voyage.auth.validator.AuthValidator;
import com.ssafy.voyage.dto.member.MemberCreationDto;
import com.ssafy.voyage.exception.MemberFormValidationException;
import com.ssafy.voyage.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
// Transactional 붙이지 마
@RequiredArgsConstructor
public class AuthService {
    private final MemberService memberService;

    private final JwtService jwtService;
    private final AuthValidator authValidator;

    // 회원가입
    @Transactional
    public void signUp(MemberCreationDto memberCreationDto) throws MemberFormValidationException, DataIntegrityViolationException {
        // 회원 가입 정보 검증
        authValidator.validateMemberCreationDto(memberCreationDto);

        memberService.createMember(memberCreationDto);
    }

    // 로그아웃
    // Transactional 필요 없음
    public void signOut(String refreshToken) {
        // 해당 refreshToken 삭제
        jwtService.deleteRefreshToken(refreshToken);
    }
}

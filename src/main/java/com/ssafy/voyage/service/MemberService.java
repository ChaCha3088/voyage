package com.ssafy.voyage.service;

import com.ssafy.voyage.auth.entity.RefreshToken;
import com.ssafy.voyage.auth.repository.refreshtoken.RefreshTokenRepository;
import com.ssafy.voyage.auth.service.JwtService;
import com.ssafy.voyage.auth.validator.AuthValidator;
import com.ssafy.voyage.dto.member.MemberCreationDto;
import com.ssafy.voyage.dto.member.MemberUpdateDto;
import com.ssafy.voyage.dto.response.MemberDto;
import com.ssafy.voyage.entity.Member;
import com.ssafy.voyage.exception.MemberFormValidationException;
import com.ssafy.voyage.exception.NoSuchMemberException;
import com.ssafy.voyage.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static com.ssafy.voyage.message.message.MemberMessages.MEMBER;
import static com.ssafy.voyage.message.message.Messages.NOT_EXISTS;
import static com.ssafy.voyage.message.message.Messages.SUCH;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {
    private final MemberRepository memberRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    private final AuthValidator authValidator;
    private final PasswordEncoder passwordEncoder;

    public MemberDto findMemberByEmail(String email) throws NoSuchMemberException {
        return getMemberByEmail(email).toMemberDto();
    }

    @Transactional
    public long createMember(MemberCreationDto memberCreationDto) throws DataIntegrityViolationException {
        Member newMember = Member.builder()
                .email(memberCreationDto.getEmail())
                .password(passwordEncoder.encode(memberCreationDto.getPassword()))
                .name(memberCreationDto.getName())
                .build();

        return memberRepository.save(newMember).getId();
    }

    @Transactional
    public Member updateMember(MemberUpdateDto memberUpdateDto, String email) throws NoSuchMemberException, MemberFormValidationException {
        // 비밀번호 재입력이 일치하지 않으면, 예외 발생
        authValidator.isValidPassword(email, memberUpdateDto.getPassword(), memberUpdateDto.getPasswordAgain());

        Member member = getMemberByEmail(email);

        member.changePassword(passwordEncoder.encode(memberUpdateDto.getPassword()));

        memberRepository.save(member);

        return member;
    }

    @Transactional
    public ResponseEntity deleteMember(String email, HttpServletResponse response) throws NoSuchMemberException, IOException {
        Member member = memberRepository.findNotDeletedByEmailWithRefreshToken(email)
                .orElseThrow(() -> new NoSuchMemberException(new StringBuffer().append(SUCH.getMessage()).append(MEMBER.getMessage()).append(NOT_EXISTS.getMessage()).toString()));

        // 발급한 RefreshToken 전부 지우세요
        for (RefreshToken refreshToken : member.getRefreshTokens()) {
            refreshTokenRepository.delete(refreshToken);
        }

        memberRepository.delete(member);

        response.setHeader(JwtService.ACCESS_TOKEN_HEADER, "");

        response.sendRedirect("/");

        return ResponseEntity
                .noContent()
                .build();
    }

    private Member getMemberByEmail(String email) {
        return memberRepository.findNotDeletedByEmail(email)
                .orElseThrow(() -> new NoSuchMemberException(new StringBuffer().append(SUCH.getMessage()).append(MEMBER.getMessage()).append(NOT_EXISTS.getMessage()).toString()));
    }
}

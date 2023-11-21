package com.ssafy.voyage.service;

import com.amazonaws.SdkBaseException;
import com.ssafy.voyage.auth.entity.RefreshToken;
import com.ssafy.voyage.auth.repository.refreshtoken.RefreshTokenRepository;
import com.ssafy.voyage.auth.service.JwtService;
import com.ssafy.voyage.auth.validator.AuthValidator;
import com.ssafy.voyage.dto.member.MemberCreationDto;
import com.ssafy.voyage.dto.member.MemberPasswordUpdateDto;
import com.ssafy.voyage.dto.request.MemberProfileImageUpdateDto;
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
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

import static com.ssafy.voyage.message.message.ImageMessages.MULTIPART_FILE;
import static com.ssafy.voyage.message.message.MemberMessages.MEMBER;
import static com.ssafy.voyage.message.message.Messages.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {
    private final S3Service s3Service;

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
    public Member updatePassword(MemberPasswordUpdateDto memberPasswordUpdateDto, String email) throws NoSuchMemberException, MemberFormValidationException {
        // 비밀번호 재입력이 일치하지 않으면, 예외 발생
        authValidator.isValidPassword(email, memberPasswordUpdateDto.getPassword(), memberPasswordUpdateDto.getPasswordAgain());

        Member member = getMemberByEmail(email);

        member.changePassword(passwordEncoder.encode(memberPasswordUpdateDto.getPassword()));

        memberRepository.save(member);

        return member;
    }

    @Transactional(rollbackFor = IOException.class)
    public Member updateProfileImage(List<MultipartFile> multipartFiles, String email) throws NoSuchMemberException, IllegalArgumentException, SdkBaseException, IOException {
        // multipartFile이 1개가 아니면, 예외 발생
        if (multipartFiles.size() != 1) {
            throw new IllegalArgumentException(new StringBuffer().append(MULTIPART_FILE.getMessage()).append(ONLY.getMessage()).append(ONE.getMessage()).append(VALID.getMessage()).toString());
        }

        Member member = getMemberByEmail(email);

        // 기존 프로필이 없다면
        if (member.getProfileImageUrl() == null) {
            // 새로운 프로필 사진을 업로드한다.
            String newAmazonUrl = s3Service.uploadImageToS3(member.getEmail(), multipartFiles.get(0));

            // 업로드한 프로필 사진의 URL을 저장한다.
            member.setProfileImage(newAmazonUrl);
        }
        // 기존 프로필이 있다면
        else {
            // 기존 프로필 사진을 삭제한다.
            s3Service.deleteFromS3(member.getProfileImageUrl());

            // 새로운 프로필 사진을 업로드한다.
            String newAmazonUrl = s3Service.uploadImageToS3(member.getEmail(), multipartFiles.get(0));

            // 업로드한 프로필 사진의 URL을 저장한다.
            member.setProfileImage(newAmazonUrl);
        }

        memberRepository.save(member);

        return member;
    }

    @Transactional
    public void deleteProfileImage(String email) throws IllegalArgumentException, SdkBaseException {
        Member member = getMemberByEmail(email);

        // 기존 프로필이 있다면
        if (member.getProfileImageUrl() != null) {
            // 기존 프로필 사진을 삭제한다.
            s3Service.deleteFromS3(member.getProfileImageUrl());

            // 프로필 사진을 null로 초기화한다.
            member.setProfileImage(null);

            memberRepository.save(member);
        }
    }

    @Transactional
    public void deleteMember(String email, HttpServletResponse response) throws NoSuchMemberException,IllegalArgumentException, SdkBaseException {
        Member member = memberRepository.findNotDeletedByEmailWithRefreshToken(email)
                .orElseThrow(() -> new NoSuchMemberException(new StringBuffer().append(SUCH.getMessage()).append(MEMBER.getMessage()).append(NOT_EXISTS.getMessage()).toString()));

        // 발급한 RefreshToken 전부 지우세요
        for (RefreshToken refreshToken : member.getRefreshTokens()) {
            refreshTokenRepository.delete(refreshToken);
        }

        memberRepository.delete(member);
        // 업로드한 프로필 사진 삭제
        if (member.getProfileImageUrl() != null) {
            // 프로필 사진을 삭제한다.
            s3Service.deleteFromS3(member.getProfileImageUrl());
        }

        response.setHeader(JwtService.ACCESS_TOKEN_HEADER, "");
    }

    private Member getMemberByEmail(String email) {
        return memberRepository.findNotDeletedByEmail(email)
                .orElseThrow(() -> new NoSuchMemberException(new StringBuffer().append(SUCH.getMessage()).append(MEMBER.getMessage()).append(NOT_EXISTS.getMessage()).toString()));
    }
}

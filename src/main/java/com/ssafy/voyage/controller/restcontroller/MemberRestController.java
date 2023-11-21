package com.ssafy.voyage.controller.restcontroller;

import com.amazonaws.SdkBaseException;
import com.ssafy.voyage.auth.exception.AuthorizationException;
import com.ssafy.voyage.dto.member.MemberPasswordUpdateDto;
import com.ssafy.voyage.dto.response.MemberDto;
import com.ssafy.voyage.entity.Member;
import com.ssafy.voyage.exception.MemberFormValidationException;
import com.ssafy.voyage.exception.NoSuchMemberException;
import com.ssafy.voyage.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(value = "/api/member", consumes = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class MemberRestController {
    private static final String PREFIX = "/api/member/";
    private final MemberService memberService;

    // 회원 조회
    @GetMapping
    public ResponseEntity findMemberByEmail(HttpServletRequest request) throws NoSuchMemberException {
        String email = (String) request.getAttribute("email");

        MemberDto memberDto = memberService.findMemberByEmail(email);

        return ResponseEntity.ok()
            .body(
                MemberDto
                    .builder()
                    .email(memberDto.getEmail())
                    .name(memberDto.getName())
                    .profileImageUrl(memberDto.getProfileImageUrl())
                    .build()
            );
    }

    // 회원 비밀번호 수정
    @PutMapping("/password")
    public ResponseEntity updatePassword(@Valid @RequestBody MemberPasswordUpdateDto memberPasswordUpdateDto, HttpServletRequest request) throws NoSuchMemberException, MemberFormValidationException {
        String email = (String) request.getAttribute("email");

        memberService.updatePassword(memberPasswordUpdateDto, email);

        return ResponseEntity.noContent().build();
    }

    // 회원 프로필 사진 수정
    @PostMapping(value = "/profile-image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity updateProfileImage(@RequestParam(value = "ProfileImage") List<MultipartFile> multipartFiles, HttpServletRequest request) throws NoSuchMemberException, IllegalArgumentException, SdkBaseException, IOException {
        String email = (String) request.getAttribute("email");

        Member updatedMember = memberService.updateProfileImage(multipartFiles, email);

        return ResponseEntity.ok()
            .body(
                MemberDto
                    .builder()
                    .email(updatedMember.getEmail())
                    .name(updatedMember.getName())
                    .profileImageUrl(updatedMember.getProfileImageUrl())
                    .build()
            );
    }

    // 회원 프로필 사진 삭제
    @DeleteMapping("/profile-image")
    public ResponseEntity deleteProfileImage(HttpServletRequest request) {
        String email = (String) request.getAttribute("email");

        memberService.deleteProfileImage(email);

        return ResponseEntity.noContent().build();
    }

    // 회원 탈퇴
    @DeleteMapping
    public ResponseEntity deleteMember(HttpServletRequest request, HttpServletResponse response) throws NoSuchMemberException {
        String email = (String) request.getAttribute("email");

        memberService.deleteMember(email, response);

        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler({NoSuchMemberException.class, IllegalArgumentException.class, MemberFormValidationException.class, IOException.class})
    public ResponseEntity handleBadRequest(RuntimeException e) {
        return ResponseEntity.badRequest()
            .body(e.getMessage()
            );
    }

    @ExceptionHandler(AuthorizationException.class)
    public ResponseEntity handleAuthorizationException(AuthorizationException e) {
        return ResponseEntity.badRequest()
            .body(e.getMessage()
            );
    }
}

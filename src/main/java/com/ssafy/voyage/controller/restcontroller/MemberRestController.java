package com.ssafy.voyage.controller.restcontroller;

import com.ssafy.voyage.auth.exception.AuthorizationException;
import com.ssafy.voyage.dto.member.MemberUpdateDto;
import com.ssafy.voyage.dto.response.MemberDto;
import com.ssafy.voyage.entity.Member;
import com.ssafy.voyage.exception.MemberFormValidationException;
import com.ssafy.voyage.exception.NoSuchMemberException;
import com.ssafy.voyage.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequestMapping("/api/member")
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
                    .build()
            );
    }

    // 비밀번호 수정
    @PutMapping
    public ResponseEntity updateMember(@Valid @RequestBody MemberUpdateDto memberUpdateDto, HttpServletRequest request, HttpServletResponse response) throws NoSuchMemberException, IOException {
        String email = (String) request.getAttribute("email");

        Member updatedMember = memberService.updateMember(memberUpdateDto, email);

        return ResponseEntity.ok()
            .body(
                MemberDto
                    .builder()
                    .email(updatedMember.getEmail())
                    .name(updatedMember.getName())
                    .build()
            );
    }

    // 회원 탈퇴
    @DeleteMapping
    public void deleteMember(HttpServletRequest request, HttpServletResponse response) throws NoSuchMemberException, IOException {
        String email = (String) request.getAttribute("email");

        memberService.deleteMember(email, response);
    }

    @ExceptionHandler(NoSuchMemberException.class)
    public ResponseEntity handleNoSuchMemberException(NoSuchMemberException e) {
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

    @ExceptionHandler(MemberFormValidationException.class)
    public ResponseEntity handleMemberFormValidationException(MemberFormValidationException e) {
        return ResponseEntity.badRequest()
            .body(e.getMessage()
            );
    }
}

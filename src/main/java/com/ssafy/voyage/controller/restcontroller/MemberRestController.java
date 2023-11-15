package com.ssafy.voyage.controller.restcontroller;

import com.ssafy.voyage.dto.member.MemberCreationDto;
import com.ssafy.voyage.dto.member.MemberUpdateDto;
import com.ssafy.voyage.dto.response.MemberResponseDto;
import com.ssafy.voyage.entity.Member;
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
    private final MemberService memberService;
    private static final String PREFIX = "/api/member/";

    @GetMapping
    public ResponseEntity findMemberByEmail(HttpServletRequest request) throws NoSuchMemberException {
        String email = (String) request.getAttribute("email");

        Member member = memberService.findMemberByEmail(email);

        return ResponseEntity.ok()
            .body(
                MemberResponseDto
                    .builder()
                    .email(member.getEmail())
                    .name(member.getName())
                    .build()
            );
    }

    @PostMapping
    public void createMember(@Valid @RequestBody MemberCreationDto memberCreationDto, HttpServletResponse response) throws IOException {
        long newMemberId = memberService.createMember(memberCreationDto);

        response.sendRedirect(PREFIX + newMemberId);
    }

    @PutMapping
    public void updateMember(@Valid @RequestBody MemberUpdateDto memberUpdateDto, HttpServletRequest request, HttpServletResponse response) throws NoSuchMemberException, IOException {
        String email = (String) request.getAttribute("email");

        long memberId = memberService.updateMember(memberUpdateDto, email);

        response.sendRedirect(PREFIX + memberId);
    }

    @DeleteMapping
    public void deleteMember(HttpServletRequest request, HttpServletResponse response) throws NoSuchMemberException, IOException {
        String email = (String) request.getAttribute("email");

        memberService.deleteMember(email);

        response.sendRedirect(PREFIX);
    }

    @ExceptionHandler(NoSuchMemberException.class)
    public ResponseEntity handleNoSuchMemberException(NoSuchMemberException e) {
        return ResponseEntity.badRequest()
            .body(e.getMessage());
    }
}

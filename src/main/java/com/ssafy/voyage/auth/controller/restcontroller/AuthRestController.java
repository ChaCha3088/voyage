package com.ssafy.voyage.auth.controller.restcontroller;

import com.ssafy.voyage.auth.validator.Validator;
import com.ssafy.voyage.dto.member.MemberCreationDto;
import com.ssafy.voyage.dto.response.Response;
import com.ssafy.voyage.exception.MemberCreationValidationException;
import com.ssafy.voyage.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

import static com.ssafy.voyage.message.cause.MemberCause.FORM;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthRestController {
    private final MemberService memberService;
    private final Validator validator;

    @PostMapping("/signup/v1")
    public ResponseEntity signUpV1(@RequestBody MemberCreationDto memberCreationDto) throws MemberCreationValidationException {
        // 회원 가입 정보 검증
        validator.validateMemberCreationDto(memberCreationDto);

        memberService.createMember(memberCreationDto);

        return ResponseEntity.created(
            URI.create("/api/auth/signin")
        ).build();
    }

    @ExceptionHandler({MemberCreationValidationException.class, MethodArgumentNotValidException.class})
    public ResponseEntity handleMemberCreationValidationException(Exception e) {
        return ResponseEntity.badRequest().body(
            Response.builder()
                .cause(FORM)
                .message(e.getMessage())
                .build()
        );
    }

    // DB Unique Constraint Violation Exception
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity handleDataIntegrityViolationException(DataIntegrityViolationException e) {
        return ResponseEntity.badRequest().body(
            Response.builder()
                .cause(FORM)
                .message(e.getMessage())
                .build()

        );
    }
}

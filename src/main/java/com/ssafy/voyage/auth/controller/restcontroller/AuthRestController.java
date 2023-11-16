package com.ssafy.voyage.auth.controller.restcontroller;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.ssafy.voyage.auth.exception.NoSuchRefreshTokenInDBException;
import com.ssafy.voyage.auth.message.JwtCauses;
import com.ssafy.voyage.auth.service.AuthService;
import com.ssafy.voyage.auth.service.JwtService;
import com.ssafy.voyage.dto.member.MemberCreationDto;
import com.ssafy.voyage.dto.response.Response;
import com.ssafy.voyage.exception.MemberFormValidationException;
import com.ssafy.voyage.exception.NoSuchMemberException;
import com.ssafy.voyage.message.cause.MemberCause;
import com.ssafy.voyage.message.message.MemberMessages;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;
import java.net.URI;

import static com.ssafy.voyage.auth.message.JwtMessages.JWT;
import static com.ssafy.voyage.auth.message.JwtMessages.REISSUE;
import static com.ssafy.voyage.message.cause.MemberCause.EMAIL;
import static com.ssafy.voyage.message.cause.MemberCause.FORM;
import static com.ssafy.voyage.message.message.Messages.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthRestController {
    private final AuthService authService;
    private final JwtService jwtService;

    // 회원가입
    @PostMapping("/signup/v1")
    public ResponseEntity signUpV1(@RequestBody MemberCreationDto memberCreationDto) throws MemberFormValidationException {
        authService.signUp(memberCreationDto);

        return ResponseEntity.created(
            URI.create("/api/auth/signin/v1")
        ).build();
    }

    // 로그인은 여기서 처리하지 않는다.
    // MemberAuthenticationFilter에서 처리 시작

    // JWT 토큰 재발급
    @GetMapping("/reissue/v1")
    public ResponseEntity reissueJwtsV1(@RequestParam @NotBlank String redirectUrl, HttpServletRequest request, HttpServletResponse response) {
        String[] emailAndRefreshToken = jwtService.validateAndExtractEmailFromRefreshToken(request);

        String[] jwts = jwtService.reissueJwts(emailAndRefreshToken[0], emailAndRefreshToken[1]);

        // Header에 accessToken, refreshToken 담기
        jwtService.setAccessTokenOnHeader(response, jwts[0]);
        jwtService.setRefreshTokenOnHeader(response, jwts[1]);

        response.setHeader("Location", redirectUrl);

        return ResponseEntity.ok(
            Response.builder()
                .cause("")
                .message(new StringBuffer().append(JWT.getMessage()).append(REISSUE.getMessage()).append(SUCCESS.getMessage()).toString())
                .build()
        );
    }

    // 회원가입 유효성 검사
    @ExceptionHandler(MemberFormValidationException.class)
    public ResponseEntity handleMemberCreationValidationException(Exception e) {
        return ResponseEntity.badRequest().body(
            Response.builder()
                .cause(FORM.getMessage())
                .message(e.getMessage())
                .build()
        );
    }

    // DB Unique Constraint Violation DataIntegrityViolationException
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity handleDataIntegrityViolationException(DataIntegrityViolationException e) {
        return ResponseEntity.badRequest().body(
            Response.builder()
                .cause(EMAIL.getMessage())
                .message(new StringBuffer().append(MemberMessages.EMAIL.getMessage()).append(ALREADY.getMessage()).append(EXISTS.getMessage()).toString())
                .build()
        );
    }

    // Header에 refreshToken 값 없을 때
    // IllegalArgumentException
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity handleIllegalArgumentException(IllegalArgumentException e) {
        return ResponseEntity.badRequest().body(
            Response.builder()
                .cause(JwtCauses.JWT.getMessage())
                .message(e.getMessage())
                .build()
        );
    }

    // Refresh Token이 유효하지 않거나, DB에 없을 때
    // JWTVerificationException || NoSuchRefreshTokenInDBException
    @ExceptionHandler({JWTVerificationException.class, NoSuchRefreshTokenInDBException.class})
    public ResponseEntity handleInvalidJWT(RuntimeException e) {
        Response response = Response.builder()
            .cause(JwtCauses.JWT.getMessage())
            .message(e.getMessage())
            .build();

        return ResponseEntity
            .status(HttpStatus.UNAUTHORIZED)
            .body(response);
    }

    // 회원이 없을 때
    // NoSuchMemberException
    @ExceptionHandler({NoSuchMemberException.class})
    public ResponseEntity handleNoSuchMemberException(NoSuchMemberException e) {
        return ResponseEntity.badRequest().body(
            Response.builder()
                .cause(MemberCause.MEMBER.getMessage())
                .message(e.getMessage())
                .build()
        );
    }
}

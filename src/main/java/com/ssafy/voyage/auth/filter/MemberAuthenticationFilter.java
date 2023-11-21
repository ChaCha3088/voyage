package com.ssafy.voyage.auth.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.voyage.auth.dto.SignInRequestDto;
import com.ssafy.voyage.auth.handler.MemberSignInFailureHandler;
import com.ssafy.voyage.auth.handler.MemberSignInSuccessHandler;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.util.StreamUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static com.ssafy.voyage.message.message.Messages.*;
import static com.ssafy.voyage.message.message.MemberMessages.EMAIL;
import static com.ssafy.voyage.message.message.MemberMessages.PASSWORD;

public class MemberAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
    private final ObjectMapper objectMapper;
    private final MemberSignInSuccessHandler memberSignInSuccessHandler;
    private final MemberSignInFailureHandler memberSignInFailureHandler;

    public MemberAuthenticationFilter(ObjectMapper objectMapper, AuthenticationManager authenticationManager, MemberSignInSuccessHandler memberSignInSuccessHandler, MemberSignInFailureHandler memberSignInFailureHandler) {
        super("/api/auth/signin/v1", authenticationManager);

        this.objectMapper = objectMapper;
        this.memberSignInSuccessHandler = memberSignInSuccessHandler;
        this.memberSignInFailureHandler = memberSignInFailureHandler;

        setAuthenticationManager(authenticationManager);
        setAuthenticationSuccessHandler(memberSignInSuccessHandler);
        setAuthenticationFailureHandler(memberSignInFailureHandler);
    }

    //로그인 요청이 들어오면 로그인 시도하는 메소드
    //1. username, password 받아서
    //2. 정상인지 로그인 시도 -> AuthenticationManager로 로그인 시도
    //3. PrincipalDetailsService 호출 -> loadUserByUsername() 함수 실행
    //4. PrincipalDetails를 세션에 담고 (권한 관리를 위해서)
    //5. JWT를 만들어서 응답해주면 됨
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException {
        // Content-Type이 null이거나, application/json이 아니거나, method가 POST가 아니면 예외 발생
        if (request.getContentType() == null || !request.getContentType().contains(MediaType.APPLICATION_JSON_VALUE) || !request.getMethod().equals("POST")) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);

            throw new AuthenticationException("Authentication method not supported: " + request.getMethod() + " " + request.getContentType()) {
                @Override
                public String getMessage() {
                    return super.getMessage();
                }
            };
        }

        // request의 body에서 email과 password를 받아서 SignInAPIRequest 객체로 만든다.
        SignInRequestDto signInRequestDto = objectMapper.readValue(StreamUtils.copyToString(request.getInputStream(), StandardCharsets.UTF_8), SignInRequestDto.class);

        String email = signInRequestDto.getEmail();
        String password = signInRequestDto.getPassword();

        // email이나 password가 없으면 예외 발생
        if (email == null || password == null || email.isBlank() || password.isBlank()) {
            throw new AuthenticationException(new StringBuffer().append(EMAIL).append(OR).append(PASSWORD).append(NOT_FOUND).toString()) {
                @Override
                public String getMessage() {
                    return super.getMessage();
                }
            };
        }

        // authentication 생성
        Authentication authentication = new UsernamePasswordAuthenticationToken(email, password);

        return super.getAuthenticationManager().authenticate(authentication);

        // MemberAuthenticationProvider의 authenticate() 함수가 실행됨
    }
}

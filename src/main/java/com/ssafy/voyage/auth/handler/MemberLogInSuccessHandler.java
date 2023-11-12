package com.ssafy.voyage.auth.handler;

import com.ssafy.voyage.exception.NoSuchMemberException;
import com.ssafy.voyage.service.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.ssafy.voyage.service.jwt.JwtService.*;

@Component
@RequiredArgsConstructor
public class MemberLogInSuccessHandler implements AuthenticationSuccessHandler {
    private final JwtService jwtService;

    /**
     * 로그인 성공 시, JwtEntity를 생성하고 AccessToken과 RefreshToken을 Cookie에 담아 보낸다.
     * @param request the request which caused the successful authentication
     * @param response the response
     * @param authentication the <tt>Authentication</tt> object which was created during
     * the authentication process.
     * @throws IOException
     * @throws ServletException
     */
    @Transactional
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
//        try {
//            String email = authentication.getName();
//
//            // token들을 발급한다.
//            String[] accessTokenAndRefreshToken = jwtService.issueTokens(email);
//
//            // access token, refresh token을 헤더에 실어서 보낸다.
//            response.setHeader(ACCESS_TOKEN_HEADER, BEARER + accessTokenAndRefreshToken[0]);
//            response.setHeader(REFRESH_TOKEN_HEADER, BEARER + accessTokenAndRefreshToken[1]);
//
//        } catch (NoSuchMemberException e) {
//            // 회원을 찾을 수 없거나, DeviceToken이 없으면, 로그인 페이지로 이동
//            response.sendRedirect("/auth/login");
//        }
    }
}

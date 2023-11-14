package com.ssafy.voyage.auth.handler;

import com.ssafy.voyage.auth.service.AuthService;
import com.ssafy.voyage.exception.NoSuchMemberException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.ssafy.voyage.auth.service.JwtService.*;

@Component
@RequiredArgsConstructor
public class MemberSignInSuccessHandler implements AuthenticationSuccessHandler {
    private final AuthService authService;

    /**
     * 로그인 성공 시, JwtEntity를 생성하고 AccessToken과 RefreshToken을 Header에 담아 보낸다.
     * @param request the request which caused the successful authentication
     * @param response the response
     * @param authentication the <tt>Authentication</tt> object which was created during
     * the authentication process.
     * @throws IOException
     * @throws ServletException
     */
    // Transactional 붙이지 말 것
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        try {
            String email = authentication.getName();

            // Transaction 있음
            String[] accessTokenAndRefreshToken = authService.whenMemberSignIn_IssueJwts(email);

            // access token, refresh token을 헤더에 실어서 보낸다.
            response.setHeader(ACCESS_TOKEN_HEADER, BEARER + accessTokenAndRefreshToken[0]);
            response.setHeader(REFRESH_TOKEN_HEADER, BEARER + accessTokenAndRefreshToken[1]);
        }
        catch (NoSuchMemberException e) {
            // 회원을 찾을 수 없으면, 로그인 페이지로 이동
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.sendRedirect("/api/signin/v1");
        }
    }
}

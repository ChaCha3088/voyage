package com.ssafy.voyage.auth.handler;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.ssafy.voyage.auth.service.AuthService;
import com.ssafy.voyage.auth.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

import static com.ssafy.voyage.auth.message.JwtMessages.REFRESH_TOKEN;
import static com.ssafy.voyage.auth.service.JwtService.ACCESS_TOKEN_HEADER;
import static com.ssafy.voyage.auth.service.JwtService.REFRESH_TOKEN_HEADER;
import static com.ssafy.voyage.message.message.Messages.NOT_FOUND;
import static javax.servlet.http.HttpServletResponse.SC_BAD_REQUEST;
import static javax.servlet.http.HttpServletResponse.SC_NO_CONTENT;

@Component
@RequiredArgsConstructor
public class JwtSignOutHandler implements LogoutHandler {
    private final AuthService authService;
    private final JwtService jwtService;

    /**
     * 로그아웃 할 때는 accessToken과 refreshToken을 모두 보내야 함
     * @param request the HTTP request
     * @param response the HTTP response
     * @param authentication the current principal details
     */
    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        try {
            // refreshToken
            String[] emailAndRefreshToken = jwtService.validateAndExtractEmailFromRefreshToken(request);

            authService.signOut(emailAndRefreshToken[1]);

            response.setHeader(ACCESS_TOKEN_HEADER, "");
        }
        // 들어오는 값이 이상할 때 || refreshToken이 유효하지 않을 때
        catch (IllegalArgumentException | JWTVerificationException e) {
            response.setStatus(SC_BAD_REQUEST);
        }
    }
}

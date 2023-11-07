package com.ssafy.voyage.auth.handler;

import com.ssafy.voyage.message.MessageMaker;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

import static com.ssafy.voyage.message.message.Messages.EMPTY;
import static com.ssafy.voyage.message.message.Messages.NOT_EQUAL;
import static com.ssafy.voyage.message.message.PasswordMessages.PASSWORD;

@Component
@RequiredArgsConstructor
public class MemberLogInFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
        // 해당 회원이 없는 경우
        if (exception instanceof UsernameNotFoundException) {
            // 로그인 페이지로 redirect
            response.sendRedirect("/auth/login?error=email&exception=" + URLEncoder.encode(exception.getMessage(), "UTF-8"));
            return;
        }

        String email = request.getParameter("email");

        // 비밀번호가 틀린 경우
        if (exception.getMessage().equals(MessageMaker.getMessageMaker().add(PASSWORD).add(NOT_EQUAL).toString())) {
            response.sendRedirect("/auth/login?error=password&exception=" + URLEncoder.encode(exception.getMessage(), "UTF-8") + "&email=" + email);
            return;
        }
    }
}

package com.ssafy.voyage.interceptor;

import com.ssafy.voyage.auth.exception.AuthorizationException;
import com.ssafy.voyage.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

import static com.ssafy.voyage.auth.message.AuthMessages.AUTHORIZATION;
import static com.ssafy.voyage.message.message.Messages.NOT_FOUND;

@Component
@RequiredArgsConstructor
public class MemberAuthorizationInterceptor implements HandlerInterceptor {
    private final MemberRepository memberRepository;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws AuthorizationException {
        // email이 없으면, 인증 실패
        String email = (String) Optional.ofNullable(request.getAttribute("email"))
            .orElseThrow(() -> new AuthorizationException(new StringBuffer().append(AUTHORIZATION.getMessage()).append(NOT_FOUND.getMessage()).toString()));

        // email이 있으면, 성공
        return true;
    }
}

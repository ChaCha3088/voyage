package com.ssafy.voyage.auth.provider;

import com.ssafy.voyage.auth.service.PrincipalUserDetailsService;
import com.ssafy.voyage.message.MessageMaker;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import static com.ssafy.voyage.message.message.Messages.NOT_EQUAL;
import static com.ssafy.voyage.message.message.PasswordMessages.PASSWORD;

@Component
@RequiredArgsConstructor
public class MemberAuthenticationProvider implements AuthenticationProvider {
    private final PrincipalUserDetailsService principalUserDetailsService;
    private final PasswordEncoder passwordEncoder;

    /**
     * 사용자가 입력한 email과 password를 검증하는 메소드
     * @param authentication the authentication request object.
     * @return
     * @throws AuthenticationException
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String email = (String) authentication.getPrincipal();

        String password = (String) authentication.getCredentials();

        // email로 회원정보 조회
        UserDetails user = principalUserDetailsService.loadUserByUsername(email);

        // 비밀번호 일치 여부 확인
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new AuthenticationException(MessageMaker.getMessageMaker().add(PASSWORD).add(NOT_EQUAL).toString()) {
                @Override
                public String getMessage() {
                    return super.getMessage();
                }
            };
        }

        return new UsernamePasswordAuthenticationToken(email, password, user.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        boolean assignableFrom = UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
        return assignableFrom;
    }
}

package com.ssafy.voyage.auth.provider;

import com.ssafy.voyage.auth.service.PrincipalUserDetailsService;
import com.ssafy.voyage.auth.userdetail.PrincipalUserDetails;
import com.ssafy.voyage.auth.enumstorage.MemberStatus;
import com.ssafy.voyage.entity.Member;
import com.ssafy.voyage.repository.MemberRepository;
import com.ssafy.voyage.auth.validator.AuthValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import static com.ssafy.voyage.auth.message.AuthMessages.SIGN_IN;
import static com.ssafy.voyage.message.message.MemberMessages.PASSWORD;
import static com.ssafy.voyage.message.message.Messages.*;

@Component
@RequiredArgsConstructor
public class MemberAuthenticationProvider implements AuthenticationProvider {
    private final PrincipalUserDetailsService principalUserDetailsService;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthValidator authValidator;

    /**
     * 사용자가 입력한 email과 password를 검증하는 메소드
     * @param authentication the authentication request object.
     * @return
     * @throws AuthenticationException
     */
    @Transactional
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // 값 검증 완료된 email, password
        String email = (String) authentication.getPrincipal();
        String password = (String) authentication.getCredentials();

        // email로 회원정보 조회
        // Transaction 있음
        UserDetails user = principalUserDetailsService.loadUserByUsername(email);

        PrincipalUserDetails principalUserDetails = (PrincipalUserDetails) user;
        Member member = principalUserDetails.getMember();

        // 잠겨있는지 확인
        if (member.getStatus() == MemberStatus.LOCKED) {
            throw new BadCredentialsException(new StringBuffer().append(SIGN_IN.getMessage()).append(ATTEMPT.getMessage()).append(EXCEEDED.getMessage()).toString());
        }

        // 비밀번호 일치 여부 확인
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new BadCredentialsException(new StringBuffer().append(INCORRECT.getMessage()).append(PASSWORD.getMessage()).toString());
        }

        // 로그인 성공했으므로, 로그인 시도 횟수 초기화
        member.resetLogInAttempt();
        memberRepository.save(member);

        return new UsernamePasswordAuthenticationToken(email, password, user.getAuthorities());

        // MemberSignInSuccessHandler로 이동
    }

    @Override
    public boolean supports(Class<?> authentication) {
        boolean assignableFrom = UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
        return assignableFrom;
    }
}

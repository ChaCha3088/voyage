package com.ssafy.voyage.auth.filter;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.ssafy.voyage.auth.service.JwtService;
import com.ssafy.voyage.auth.userdetail.PrincipalUserDetails;
import com.ssafy.voyage.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;


/**
 * /auth/login 이외의 요청이 들어올 때, access token이 유효한지 검증하고 인증 처리/인증 실패/토큰 재발급 등을 수행
 */
@Component
@RequiredArgsConstructor
public class AuthenticationProcessFilter extends OncePerRequestFilter {
    private final JwtService jwtService;

    private static final List<String> NO_CHECK_URL = Arrays.asList("/api/auth", "/api/sido");
    private static final String ATTRACTION = "/api/attraction";

    /**
     * "/auth/login"으로 시작하는 URL 요청은 logIn 검증 및 authenticate X
     * 그 외의 URL 요청은 access token 검증 및 authenticate 수행
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (request.getRequestURI().equals("/") || isInNoCheckUrl(request.getRequestURI()) || request.getRequestURI().startsWith(ATTRACTION) || request.getRequestURI().startsWith("/error") || request.getRequestURI().startsWith("/css") || request.getRequestURI().startsWith("/js") || request.getRequestURI().startsWith("/img") || request.getRequestURI().startsWith("/favicon.ico")) {
            filterChain.doFilter(request, response); // "/auth/login"으로 시작하는 URL 요청이 들어오면, 다음 필터 호출
            return; // return으로 이후 현재 필터 진행 막기 (안해주면 아래로 내려가서 계속 필터 진행시킴)
        }

        try {
            // Header에서 accessToken 추출
            String accessToken = jwtService.extractAccessToken(request);

            // access token에서 email 검증
            String[] emailAndAccessToken = jwtService.validateAndExtractEmailFromAccessToken(request);

            // request에 email 담기
            request.setAttribute("email", emailAndAccessToken[0]);

            // Header에 accessToken 담기
            jwtService.setAccessTokenOnHeader(response, accessToken);

            filterChain.doFilter(request, response); //다음 필터 호출
            return; //return으로 이후 현재 필터 진행 막기
        }
        // accessToken이 유효하지 않으면,
        catch (JWTVerificationException e) {
            // 인증되지 않음
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

            // 원래 가려던 곳 상태 저장해주기
            response.setHeader("redirectUrl", "/api/auth/reissue/v1?redirectUrl=" + request.getRequestURI());
        }
    }

    /**
     * [인증 허가 메소드]
     * 파라미터의 유저 : 우리가 만든 회원 객체 / 빌더의 유저 : UserDetails의 User 객체
     *
     * new UsernamePasswordAuthenticationToken()로 인증 객체인 Authentication 객체 생성
     * UsernamePasswordAuthenticationToken의 파라미터
     * 1. 위에서 만든 UserDetailsUser 객체 (유저 정보)
     * 2. credential(보통 비밀번호로, 인증 시에는 보통 null로 제거)
     * 3. Collection < ? extends GrantedAuthority>로,
     * UserDetails의 User 객체 안에 Set<GrantedAuthority> authorities이 있어서 getter로 호출한 후에,
     * new NullAuthoritiesMapper()로 GrantedAuthoritiesMapper 객체를 생성하고 mapAuthorities()에 담기
     *
     * SecurityContextHolder.getContext()로 SecurityContext를 꺼낸 후,
     * setAuthentication()을 이용하여 위에서 만든 Authentication 객체에 대한 인증 허가 처리
     */
    private void saveAuthentication(Member member) {
        UserDetails userDetails = new PrincipalUserDetails(member);

//        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();

        Authentication authentication =
                new UsernamePasswordAuthenticationToken(userDetails, null, List.of(new SimpleGrantedAuthority(member.getRole().toString())));

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private boolean isInNoCheckUrl(String url) {
        return NO_CHECK_URL.stream().anyMatch(url::startsWith);
    }
}

package com.ssafy.voyage.auth.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.ssafy.voyage.auth.dto.JwtFindDto;
import com.ssafy.voyage.auth.entity.RefreshToken;
import com.ssafy.voyage.auth.exception.NoAccessTokenException;
import com.ssafy.voyage.auth.exception.NoSuchRefreshTokenInDBException;
import com.ssafy.voyage.auth.message.JwtMessages;
import com.ssafy.voyage.entity.Member;
import com.ssafy.voyage.exception.NoSuchMemberException;
import com.ssafy.voyage.auth.repository.refreshtoken.RefreshTokenRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.voyage.repository.MemberRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Optional;
import java.util.Set;

import static com.ssafy.voyage.auth.message.JwtMessages.ACCESS_TOKEN;
import static com.ssafy.voyage.auth.message.JwtMessages.REFRESH_TOKEN;
import static com.ssafy.voyage.message.message.MemberMessages.MEMBER;
import static com.ssafy.voyage.message.message.Messages.*;

@Getter
@Service
// Transactional 붙이지 마
@RequiredArgsConstructor
public class JwtService {
    private final MemberRepository memberRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.token.access.expiration}")
    private long accessTokenExpiration;

    @Value("${jwt.token.refresh.expiration}")
    private long refreshTokenExpiration;

    public static final String ACCESS_TOKEN_HEADER = "Authorization";

    public static final String REFRESH_TOKEN_HEADER = "Authorization-refresh";

    public static final String BEARER = "Bearer ";

    @Transactional(readOnly = true)
    public JwtFindDto findJwtFindDtoByRefreshToken(String refreshToken) throws NoSuchRefreshTokenInDBException {
        return refreshTokenRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new NoSuchRefreshTokenInDBException(new StringBuffer().append(SUCH).append(REFRESH_TOKEN).append(NOT_EXISTS.getMessage()).toString()))
                .toJwtFindDto();
    }

    // access token, refresh token을 발급, DB에 저장한다.
    @Transactional
    public String[] issueJwts(String email) throws NoSuchMemberException {
        // email로 member를 찾는다.
        Member member = memberRepository.findNotDeletedByEmailWithRefreshToken(email)
            .orElseThrow(() -> new NoSuchMemberException(new StringBuffer().append(SUCH).append(MEMBER).append(NOT_EXISTS.getMessage()).toString()));

        // 기존 Refresh Token 전부 삭제
        Set<RefreshToken> refreshTokensInDB = member.getRefreshTokens();
        removeAllRefreshTokens(refreshTokensInDB);

        // refresh token을 발급한다.
        String newRefreshToken = JWT.create()
            .withIssuer("LetMeKnow")
            .withSubject("refreshToken")
            .withIssuedAt(new Date(System.currentTimeMillis()))
            .withExpiresAt(new Date(System.currentTimeMillis() + refreshTokenExpiration))
            .withClaim("email", member.getEmail())
            .withClaim("issuedTime", System.currentTimeMillis())
            .sign(Algorithm.HMAC512(secret));

        // 새로 발급한 refreshToken을 DB에 저장
        RefreshToken refreshTokenEntity = RefreshToken.builder()
            .member(member)
            .refreshToken(newRefreshToken)
            .build();

        refreshTokenRepository.save(refreshTokenEntity);
        memberRepository.save(member);

        // access token을 발급한다.
        String newAccessToken = JWT.create()
            .withIssuer("LetMeKnow")
            .withSubject("accessToken")
            .withIssuedAt(new Date(System.currentTimeMillis()))
            .withExpiresAt(new Date(System.currentTimeMillis() + accessTokenExpiration))
            .withClaim("email", email)
            .withClaim("issuedTime", System.currentTimeMillis())
            .sign(Algorithm.HMAC512(secret));

        return new String[] {newAccessToken, newRefreshToken};
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, noRollbackFor = NoSuchRefreshTokenInDBException.class)
    public String[] reissueJwts(String refreshToken) throws IllegalArgumentException, JWTVerificationException, NoSuchMemberException, NoSuchRefreshTokenInDBException {
        // refreshToken 값 검증
        if (refreshToken == null || refreshToken.isBlank()) {
            throw new IllegalArgumentException(new StringBuffer().append(REFRESH_TOKEN.getMessage()).append(NOT_FOUND.getMessage()).toString());
        }

        // refreshToken을 검증한다.
        String emailInToken = validateAndExtractEmailFromRefreshToken(refreshToken);

        Member member = memberRepository.findNotDeletedByEmailWithRefreshToken(emailInToken)
            .orElseThrow(() -> new NoSuchMemberException(new StringBuffer().append(SUCH.getMessage()).append(MEMBER.getMessage()).append(NOT_EXISTS.getMessage()).toString()));

        RefreshToken refreshTokenInDB = refreshTokenRepository.findByRefreshToken(refreshToken)
            // DB에 refreshToken이 없으면
            .orElseThrow(()
                // 예외 발생
                -> {
                // refreshToken 전부 지우기
                removeAllRefreshTokens(member.getRefreshTokens());
                memberRepository.save(member);

                return new NoSuchRefreshTokenInDBException(new StringBuffer().append(SUCH.getMessage()).append(REFRESH_TOKEN.getMessage()).append(NOT_EXISTS.getMessage()).toString());
            });

        // 원래 있는 refresh Token 전부 날린다.
        removeAllRefreshTokens(member.getRefreshTokens());

        // refresh token을 발급한다.
        String newRefreshToken = JWT.create()
            .withIssuer("LetMeKnow")
            .withSubject("refreshToken")
            .withIssuedAt(new Date(System.currentTimeMillis()))
            .withExpiresAt(new Date(System.currentTimeMillis() + refreshTokenExpiration))
            .withClaim("email", emailInToken)
            .withClaim("issuedTime", System.currentTimeMillis())
            .sign(Algorithm.HMAC512(secret));

        // access token을 발급한다.
        String newAccessToken = JWT.create()
            .withIssuer("LetMeKnow")
            .withSubject("accessToken")
            .withIssuedAt(new Date(System.currentTimeMillis()))
            .withExpiresAt(new Date(System.currentTimeMillis() + accessTokenExpiration))
            .withClaim("email", emailInToken)
            .withClaim("issuedTime", System.currentTimeMillis())
            .sign(Algorithm.HMAC512(secret));

        // 새 refresh Token을 저장한다.
        RefreshToken newRefreshTokenEntity = RefreshToken.builder()
            .refreshToken(newRefreshToken)
            .member(member)
            .build();

        refreshTokenRepository.save(newRefreshTokenEntity);
        memberRepository.save(member);

        return new String[] {newAccessToken, newRefreshToken};
    }

    @Transactional(noRollbackFor = JWTVerificationException.class)
    public String deleteRefreshToken(String refreshToken) throws JWTVerificationException {
        try {
            // refreshToken을 검증한다.
            String emailInToken = validateAndExtractEmailFromRefreshToken(refreshToken);

            // refreshToken을 삭제한다.
            refreshTokenRepository.deleteByRefreshToken(refreshToken);

            return emailInToken;
        }
        // refreshToken이 유효하지 않으면
        catch (JWTVerificationException e) {
            throw new JWTVerificationException(new StringBuffer().append(JwtMessages.JWT.getMessage()).append(INVALID.getMessage()).toString());
        }
    }

    /**
     * 헤더에서 AccessToken 추출
     * 토큰 형식 : Bearer XXX에서 Bearer를 제외하고 순수 토큰만 가져오기 위해서
     * 헤더를 가져온 후 "Bearer"를 삭제(""로 replace)
     */
    public String extractAccessToken(HttpServletRequest request) throws NoAccessTokenException {
        return Optional.ofNullable(request.getHeader(ACCESS_TOKEN_HEADER))
            .orElseThrow(() -> new NoAccessTokenException(new StringBuffer().append(ACCESS_TOKEN).append(HEADER).append(NOT_FOUND).toString()))
            .replace(BEARER, "");
    }

    /**
     * 헤더에서 RefreshToken 추출
     * 토큰 형식 : Bearer XXX에서 Bearer를 제외하고 순수 토큰만 가져오기 위해서
     * 헤더를 가져온 후 "Bearer"를 삭제(""로 replace)
     */
    public String extractRefreshToken(HttpServletRequest request) throws IllegalArgumentException {
        return Optional.ofNullable(request.getHeader(REFRESH_TOKEN_HEADER))
            .orElseThrow(() -> new IllegalArgumentException(new StringBuffer().append(REFRESH_TOKEN).append(HEADER).append(NOT_FOUND).toString()))
            .replace(BEARER, "");
    }

    public String validateAndExtractEmailFromAccessToken(String accessToken) throws JWTVerificationException {
        // 토큰 유효성 검사하는 데에 사용할 알고리즘이 있는 JWT verifier builder 반환
        return JWT.require(Algorithm.HMAC512(secret))
                .withIssuer("LetMeKnow")
                .withSubject("accessToken")
                .build() // 반환된 빌더로 JWT verifier 생성
                .verify(accessToken) // accessToken을 검증하고 유효하지 않다면 예외 발생
                .getClaim("email") // claim(Email) 가져오기
                .asString();
    }

    // 유효하지 않은 refreshToken이면, 바로 DB에서 지우고, 예외 발생시킨다.
    // 값을 검증할 필요 없이 그냥 지워도 될 듯? - 해시된 비번들만 들어있으니까
    @Transactional(propagation = Propagation.REQUIRES_NEW, noRollbackFor = JWTVerificationException.class) // 부모 트랜잭션과 관계 없이 독립적으로 실행 + 예외 발생해도 롤백 X
    public String validateAndExtractEmailFromRefreshToken(String refreshToken) throws JWTVerificationException {
        try {
            String emailInRefreshToken = JWT.require(Algorithm.HMAC512(secret))
                .withIssuer("LetMeKnow")
                .withSubject("refreshToken")
                .build()
                .verify(refreshToken)
                .getClaim("email")
                .asString();

            return emailInRefreshToken;
        }
        // 문제 있는 토큰이면
        catch (JWTVerificationException e) {
            // refreshToken 지우기
            refreshTokenRepository.deleteByRefreshToken(refreshToken);
            throw new JWTVerificationException(new StringBuffer().append(JwtMessages.JWT.getMessage()).append(INVALID.getMessage()).toString());
        }
    }

    @Transactional
    public boolean isRefreshTokenExists(HttpServletResponse response, String refreshToken) throws NoSuchRefreshTokenInDBException {
        RefreshToken jwt = refreshTokenRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new NoSuchRefreshTokenInDBException(new StringBuffer().append(SUCH).append(REFRESH_TOKEN).append(NOT_EXISTS.getMessage()).toString()));

        //DB의 refresh token과 일치하는지, 만료기간이 지났는지 확인
        if (!jwt.getRefreshToken().equals(refreshToken)) {
            //DB에서 지우고
            refreshTokenRepository.delete(jwt);

//            //cookie에서 지우고
//            deleteToken("accessToken", response);
//            deleteToken("refreshToken", response);

            return false;
        }

        return true;
    }

//    public void deleteAllTokensFromClient(HttpServletResponse response) {
//        deleteToken("accessToken", response);
//        deleteToken("refreshToken", response);
//    }

//    private static void deleteToken(String token, HttpServletResponse response) {
//        Cookie tokenCookie = new Cookie(token, "");
//        tokenCookie.setMaxAge(0);
//        tokenCookie.setHttpOnly(true);
////        tokenCookie.setSecure(true);
//        tokenCookie.setPath("/");
//        response.addCookie(tokenCookie);
//    }

    /**
     * accessToken을 Header에 작성한다.
     */
    public void setAccessTokenOnHeader(HttpServletResponse response, String token) {
        response.setHeader(ACCESS_TOKEN_HEADER, BEARER + token);
    }

    /**
     * refreshToken을 Header에 작성한다.
     */
    public void setRefreshTokenOnHeader(HttpServletResponse response, String token) {
        response.setHeader(REFRESH_TOKEN_HEADER, BEARER + token);
    }

    private void removeAllRefreshTokens(Set<RefreshToken> memberRefreshTokens) {
        memberRefreshTokens.forEach(refreshToken -> {
            refreshTokenRepository.delete(refreshToken);
        });
    }
}

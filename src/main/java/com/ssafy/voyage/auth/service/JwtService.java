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
                .orElseThrow(() -> new NoSuchRefreshTokenInDBException(new StringBuffer().append(SUCH.getMessage()).append(REFRESH_TOKEN.getMessage()).append(NOT_EXISTS.getMessage()).toString()))
                .toJwtFindDto();
    }

    // access token, refresh token을 발급, DB에 저장한다.
    @Transactional
    public String[] issueJwts(String email) throws NoSuchMemberException {
        // email로 member를 찾는다.
        Member member = memberRepository.findNotDeletedByEmailWithRefreshToken(email)
                .orElseThrow(() -> new NoSuchMemberException(new StringBuffer().append(SUCH.getMessage()).append(MEMBER.getMessage()).append(NOT_EXISTS.getMessage()).toString()));

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

    @Transactional(noRollbackFor = NoSuchRefreshTokenInDBException.class)
    public String[] reissueJwts(String email, String refreshToken) throws IllegalArgumentException, JWTVerificationException, NoSuchMemberException, NoSuchRefreshTokenInDBException {
        Member member = memberRepository.findNotDeletedByEmailWithRefreshToken(email)
                .orElseThrow(() -> new NoSuchMemberException(new StringBuffer().append(SUCH.getMessage()).append(MEMBER.getMessage()).append(NOT_EXISTS.getMessage()).toString()));

        RefreshToken refreshTokenInDB = refreshTokenRepository.findByRefreshToken(refreshToken)
                // DB에 해당 refreshToken이 없으면, 유효하지 않은 refreshToken이므로
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
                .withClaim("email", email)
                .withClaim("issuedTime", System.currentTimeMillis())
                .sign(Algorithm.HMAC512(secret));

        // access token을 발급한다.
        String newAccessToken = JWT.create()
                .withIssuer("LetMeKnow")
                .withSubject("accessToken")
                .withIssuedAt(new Date(System.currentTimeMillis()))
                .withExpiresAt(new Date(System.currentTimeMillis() + accessTokenExpiration))
                .withClaim("email", email)
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

    @Transactional
    public void deleteRefreshToken(String refreshToken) throws JWTVerificationException {
        // refreshToken을 삭제한다.
        refreshTokenRepository.deleteByRefreshToken(refreshToken);
    }

    /**
     * 헤더에서 AccessToken 추출
     * 토큰 형식 : Bearer XXX에서 Bearer를 제외하고 순수 토큰만 가져오기 위해서
     * 헤더를 가져온 후 "Bearer"를 삭제(""로 replace)
     */
    public String extractAccessToken(HttpServletRequest request) throws NoAccessTokenException {
        return Optional.ofNullable(request.getHeader(ACCESS_TOKEN_HEADER))
                .orElseThrow(() -> new NoAccessTokenException(new StringBuffer().append(ACCESS_TOKEN.getMessage()).append(HEADER.getMessage()).append(NOT_FOUND.getMessage()).toString()))
                .replace(BEARER, "");
    }

    public String[] validateAndExtractEmailFromAccessToken(HttpServletRequest request) throws JWTVerificationException {
        String accessToken = Optional.ofNullable(request.getHeader(ACCESS_TOKEN_HEADER))
                .orElseThrow(() -> new IllegalArgumentException(new StringBuffer().append(ACCESS_TOKEN.getMessage()).append(NOT_FOUND.getMessage()).toString()))
                .replace(BEARER, "");

        if (accessToken.isBlank()) {
            throw new IllegalArgumentException(new StringBuffer().append(REFRESH_TOKEN.getMessage()).append(NOT_FOUND.getMessage()).toString());
        }

        try {
            // accessToken 값 검증
            String email = JWT.require(Algorithm.HMAC512(secret))
                    .withIssuer("LetMeKnow")
                    .withSubject("accessToken")
                    .build() // 반환된 빌더로 JWT verifier 생성
                    .verify(accessToken) // accessToken을 검증하고 유효하지 않다면 예외 발생
                    .getClaim("email") // claim(Email) 가져오기
                    .asString();

            return new String[]{email, accessToken};
        }
        catch (JWTVerificationException e) {
            throw new JWTVerificationException(new StringBuffer().append(ACCESS_TOKEN.getMessage()).append(INVALID.getMessage()).toString());
        }
    }

    /**
     * 헤더에서 RefreshToken 추출
     * 토큰 형식 : Bearer XXX에서 Bearer를 제외하고 순수 토큰만 가져오기 위해서
     * 헤더를 가져온 후 "Bearer"를 삭제(""로 replace)
     */
    public String[] validateAndExtractEmailFromRefreshToken(HttpServletRequest request) throws IllegalArgumentException {
        String refreshToken = Optional.ofNullable(request.getHeader(REFRESH_TOKEN_HEADER))
                .orElseThrow(() -> new IllegalArgumentException(new StringBuffer().append(REFRESH_TOKEN.getMessage()).append(NOT_FOUND.getMessage()).toString()))
                .replace(BEARER, "");

        // refreshToken 값 검증
        if (refreshToken.isBlank()) {
            throw new IllegalArgumentException(new StringBuffer().append(REFRESH_TOKEN.getMessage()).append(NOT_FOUND.getMessage()).toString());
        }

        try {
            // refreshToken을 검증한다.
            String emailInRefreshToken = JWT.require(Algorithm.HMAC512(secret))
                    .withIssuer("LetMeKnow")
                    .withSubject("refreshToken")
                    .build()
                    .verify(refreshToken)
                    .getClaim("email")
                    .asString();

            return new String[]{emailInRefreshToken, refreshToken};
        }
        catch (JWTVerificationException e) {
            throw new IllegalArgumentException(new StringBuffer().append(REFRESH_TOKEN.getMessage()).append(INVALID.getMessage()).toString());
        }
    }

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

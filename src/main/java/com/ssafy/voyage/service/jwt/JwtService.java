package com.ssafy.voyage.service.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.ssafy.voyage.auth.dto.JwtFindDto;
import com.ssafy.voyage.auth.entity.RefreshToken;
import com.ssafy.voyage.auth.exception.NoAccessTokenException;
import com.ssafy.voyage.auth.exception.NoRefreshTokenException;
import com.ssafy.voyage.auth.exception.NoSuchRefreshTokenInDBException;
import com.ssafy.voyage.entity.Member;
import com.ssafy.voyage.exception.NoSuchMemberException;
import com.ssafy.voyage.message.MessageMaker;
import com.ssafy.voyage.repository.MemberRepository;
import com.ssafy.voyage.repository.jwt.RefreshTokenRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Optional;
import java.util.Set;

import static com.ssafy.voyage.message.MemberMessages.MEMBER;
import static com.ssafy.voyage.message.message.JwtMessages.ACCESS_TOKEN;
import static com.ssafy.voyage.message.message.JwtMessages.REFRESH_TOKEN;
import static com.ssafy.voyage.message.message.Messages.*;

@Getter
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
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

    public JwtFindDto findJwtFindDtoByRefreshToken(String refreshToken) throws NoSuchRefreshTokenInDBException {
        return refreshTokenRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new NoSuchRefreshTokenInDBException(MessageMaker.getMessageMaker().add(SUCH).add(REFRESH_TOKEN).add(NOT_EXISTS).toString()))
                .toJwtFindDto();
    }

    // access token, refresh token을 발급, DB에 저장한다.
    @Transactional
    public String[] issueTokens(String email) throws NoSuchMemberException {
        //email로 member를 찾는다.
        Member member = memberRepository.findNotDeletedByEmail(email)
                .orElseThrow(() -> new NoSuchMemberException(MessageMaker.getMessageMaker().add(SUCH).add(MEMBER).add(NOT_EXISTS).toString()));

        // 만료된 것 삭제
        Set<RefreshToken> memberRefreshTokens = member.getRefreshTokens();
        removeExpiredRefreshToken(memberRefreshTokens);

        //refresh token을 발급한다.
        String newRefreshToken = JWT.create()
                .withIssuer("LetMeKnow")
                .withSubject("refreshToken")
                .withIssuedAt(new Date(System.currentTimeMillis()))
                .withExpiresAt(new Date(System.currentTimeMillis() + refreshTokenExpiration))
                .withClaim("email", member.getEmail())
                .withClaim("currentTime", System.currentTimeMillis())
                .sign(Algorithm.HMAC512(secret));

        // 새로 발급한 refreshToken을 DB에 저장
        RefreshToken newRefreshTokenEntity = RefreshToken.builder()
            .member(member)
            .refreshToken(newRefreshToken)
            .build();

        refreshTokenRepository.save(newRefreshTokenEntity);
        memberRepository.save(member);

        // access token을 발급한다.
        String newAccessToken = JWT.create()
                .withIssuer("LetMeKnow")
                .withSubject("accessToken")
                .withIssuedAt(new Date(System.currentTimeMillis()))
                .withExpiresAt(new Date(System.currentTimeMillis() + accessTokenExpiration))
                .withClaim("email", email)
                .withClaim("currentTime", System.currentTimeMillis())
                .sign(Algorithm.HMAC512(secret));

        return new String[] {newAccessToken, newRefreshToken};
    }

//    @Transactional
//    public void reissueTokensAndSetTokensOnHeader(String email, String deviceToken, String refreshToken, HttpServletResponse response) throws NoSuchMemberException {
//        //email로 member를 찾는다.
//        Member member = memberRepository.findNotDeletedByEmailWithJwtAndDeviceToken(email, deviceToken)
//                .orElseThrow(() -> new NoSuchMemberException(MemberErrorMessage.NO_SUCH_MEMBER.getMessage()));
//
//        Set<Jwt> memberJwts = member.getJwts();
//
//        // 만료된 것 삭제
//        removeExpired(memberJwts);
//
//        // 원래 있던 refreshToken을 찾아 삭제
//        memberJwts.stream()
//            .filter(jwt -> jwt.getRefreshToken().equals(refreshToken))
//            .findFirst()
//            .ifPresentOrElse(jwtEntity -> {
//                // DB에서 해당 refreshToken이 있을 때만
//                jwtEntity.removeRefreshToken();
//                jwtRepository.delete(jwtEntity);
//
//                //refresh token을 발급한다.
//                String newRefreshToken = jwt.create()
//                    .withIssuer("LetMeKnow")
//                    .withSubject("refreshToken")
//                    .withExpiresAt(new Date(System.currentTimeMillis() + refreshTokenExpiration))
//                    .withClaim("email", member.getEmail())
//                    .sign(Algorithm.HMAC512(secret));
//
//                // 새로 만든 refreshToken을 DB에 저장
//                Jwt newJwt = Jwt.builder()
//                    .member(member)
//                    .refreshToken(newRefreshToken)
//                    .build();
//
//                jwtRepository.save(newJwt);
//
//                //access token을 발급한다.
//                String newAccessToken = jwt.create()
//                    .withIssuer("LetMeKnow")
//                    .withSubject("accessToken")
//                    .withExpiresAt(new Date(System.currentTimeMillis() + accessTokenExpiration))
//                    .withClaim("email", email)
//                    .sign(Algorithm.HMAC512(secret));
//
//                //access token, refresh token을 헤더에 실어서 보낸다.
//                setAccessTokenOnHeader(response, newAccessToken);
//                setRefreshTokenOnHeader(response, newRefreshToken);
//            },
//                // DB에 해당 refreshToken이 없으면, 예외 발생
//                () -> {
//                throw new NotValidRefreshTokenException(JwtErrorMessage.NOT_VALID_JWT.getMessage())
//            });
//    }

    /**
     * 헤더에서 AccessToken 추출
     * 토큰 형식 : Bearer XXX에서 Bearer를 제외하고 순수 토큰만 가져오기 위해서
     * 헤더를 가져온 후 "Bearer"를 삭제(""로 replace)
     */
    public String extractAccessToken(HttpServletRequest request) throws NoAccessTokenException {
        return Optional.ofNullable(request.getHeader(ACCESS_TOKEN_HEADER))
            .orElseThrow(() -> new NoAccessTokenException(MessageMaker.getMessageMaker().add(ACCESS_TOKEN).add(REQUEST_HEADER).add(IN).add(NOT_EXISTS).toString()))
            .replace(BEARER, "");
    }

    /**
     * 헤더에서 RefreshToken 추출
     * 토큰 형식 : Bearer XXX에서 Bearer를 제외하고 순수 토큰만 가져오기 위해서
     * 헤더를 가져온 후 "Bearer"를 삭제(""로 replace)
     */
    public String extractRefreshToken(HttpServletRequest request) throws NoRefreshTokenException {
        return Optional.ofNullable(request.getHeader(REFRESH_TOKEN_HEADER))
            .orElseThrow(() -> new NoRefreshTokenException(MessageMaker.getMessageMaker().add(REFRESH_TOKEN).add(REQUEST_HEADER).add(IN).add(NOT_EXISTS).toString()))
            .replace(BEARER, "");
    }

    public String validateAndExtractEmailFromToken(String token) throws JWTVerificationException {
        // 토큰 유효성 검사하는 데에 사용할 알고리즘이 있는 JWT verifier builder 반환
        return JWT.require(Algorithm.HMAC512(secret))
                .build() // 반환된 빌더로 JWT verifier 생성
                .verify(token) // accessToken을 검증하고 유효하지 않다면 예외 발생
                .getClaim("email") // claim(Email) 가져오기
                .asString();
    }

    @Transactional
    public boolean isRefreshTokenExists(HttpServletResponse response, String refreshToken) throws NoSuchRefreshTokenInDBException {
        RefreshToken jwt = refreshTokenRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new NoSuchRefreshTokenInDBException(MessageMaker.getMessageMaker().add(SUCH).add(REFRESH_TOKEN).add(NOT_EXISTS).toString()));

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

    private void removeExpiredRefreshToken(Set<RefreshToken> memberRefreshTokens) {
        // 만료된거 삭제하는 로직?
        memberRefreshTokens.forEach(refreshToken -> {
            if (refreshToken.isExpired()) {
                refreshTokenRepository.delete(refreshToken);
            }
        });
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
}

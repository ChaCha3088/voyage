package com.ssafy.voyage.repository.jwt;

import com.ssafy.voyage.auth.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long>, RefreshTokenRepositoryQueryDsl {
    Optional<RefreshToken> findByRefreshToken(String refreshToken);
    Optional<RefreshToken> findByRefreshTokenWithMember(String refreshToken);
    Optional<RefreshToken> findByMemberId(long memberId);
    void deleteByRefreshToken(String refreshToken);
}

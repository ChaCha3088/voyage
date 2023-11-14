package com.ssafy.voyage.auth.repository.refreshtoken;

import com.ssafy.voyage.auth.entity.RefreshToken;

import java.util.Optional;

public interface RefreshTokenRepositoryQueryDsl {
    Optional<RefreshToken> findByRefreshTokenWithMember(String refreshToken);
}

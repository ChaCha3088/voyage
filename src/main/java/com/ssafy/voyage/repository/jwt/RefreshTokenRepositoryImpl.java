package com.ssafy.voyage.repository.jwt;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.voyage.auth.entity.RefreshToken;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;
import java.util.Optional;

import static com.ssafy.voyage.auth.entity.QRefreshToken.refreshToken1;
import static com.ssafy.voyage.entity.QMember.member;

@RequiredArgsConstructor
public class RefreshTokenRepositoryImpl implements RefreshTokenRepositoryQueryDsl {
    private final EntityManager em;

    @Override
    public Optional<RefreshToken> findByRefreshTokenWithMember(String refreshToken) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);

        return Optional.ofNullable(queryFactory
            .selectFrom(refreshToken1)
            .join(refreshToken1.member, member).fetchJoin()
            .where(refreshToken1.refreshToken.eq(refreshToken))
            .fetchOne());
    }
}

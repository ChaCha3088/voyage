package com.ssafy.voyage.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.voyage.auth.enumstorage.MemberStatus;
import com.ssafy.voyage.entity.Member;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;
import java.util.Optional;

import static com.ssafy.voyage.entity.QMember.member;

@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepositoryQueryDsl {
    private final EntityManager em;

    @Override
    public Optional<Member> findNotDeletedById(long id) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);

        return Optional.ofNullable(
                queryFactory
                        .selectFrom(member)
                        .where(member.id.eq(id)
                                .and(member.status.ne(MemberStatus.DELETED)))
                        .fetchOne()
        );
    }

    @Override
    public Optional<Member> findNotDeletedByEmail(String email) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);

        return Optional.ofNullable(
                queryFactory
                        .selectFrom(member)
                        .where(member.email.eq(email)
                                .and(member.status.ne(MemberStatus.DELETED)))
                        .fetchOne()
        );
    }

    @Override
    public Optional<Member> findNotDeletedByEmailForAuthentication(String email) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);

        return Optional.ofNullable(
                queryFactory
                        .selectFrom(member)
                        .where(member.email.eq(email)
                                .and(member.status.ne(MemberStatus.DELETED)))
                        .fetchOne()
        );
    }


    @Override
    public Optional<Member> findNotDeletedByEmailWithRefreshToken(String email) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);

        return Optional.ofNullable(
                queryFactory
                        .selectFrom(member)
                        .where(member.email.eq(email)
                                .and(member.status.ne(MemberStatus.DELETED)))
                        .leftJoin(member.refreshTokens).fetchJoin()
                        .fetchOne()
        );
    }

    @Override
    public Optional<Long> findIdByEmail(String email) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);

        return Optional.ofNullable(
                queryFactory
                        .select(member.id)
                        .from(member)
                        .where(member.email.eq(email))
                        .fetchOne()
        );
    }

    @Override
    public Optional<Long> findNotDeletedIdByEmail(String email) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);

        return Optional.ofNullable(
                queryFactory
                        .select(member.id)
                        .from(member)
                        .where(member.email.eq(email)
                                .and(member.status.ne(MemberStatus.DELETED)))
                        .fetchOne()
        );
    }
}

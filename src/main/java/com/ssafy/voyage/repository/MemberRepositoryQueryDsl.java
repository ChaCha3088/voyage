package com.ssafy.voyage.repository;

import com.ssafy.voyage.entity.Member;

import java.util.Optional;

public interface MemberRepositoryQueryDsl {
    Optional<Member> findNotDeletedById(long id);
    Optional<Member> findNotDeletedByEmail(String email);
    Optional<Member> findNotDeletedByEmailWithRefreshToken(String email);
    Optional<Long> findIdByEmail(String email);
    Optional<Long> findNotDeletedIdByEmail(String email);
}

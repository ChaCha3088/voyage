package com.ssafy.voyage.auth.service;

import com.ssafy.voyage.auth.userdetail.PrincipalUserDetails;
import com.ssafy.voyage.entity.Member;
import com.ssafy.voyage.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.ssafy.voyage.message.message.MemberMessages.MEMBER;
import static com.ssafy.voyage.message.message.Messages.NOT_EXISTS;
import static com.ssafy.voyage.message.message.Messages.SUCH;
import static org.springframework.transaction.annotation.Propagation.REQUIRES_NEW;

@Service
// Transactional 붙이지 마
@RequiredArgsConstructor
public class PrincipalUserDetailsService implements UserDetailsService {
    private final MemberRepository memberRepository;

    //이런 요청이 들어왔는데, 얘 혹시 회원이야?
    @Transactional(propagation = REQUIRES_NEW) // 부모 트랜잭션과 별개로 동작
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Member를 찾는다.
        Member member = memberRepository.findNotDeletedByEmail(email)
                // 없으면, UsernameNotFoundException 발생
                .orElseThrow(() -> new UsernameNotFoundException(new StringBuffer().append(SUCH.getMessage()).append(MEMBER).append(NOT_EXISTS.getMessage()).toString()));

        // 로그인 시도 횟수 증가
        member.countUpLogInAttempt();

        // 있으면, PrincipalUserDetails 생성
        return new PrincipalUserDetails(member);
    }
}

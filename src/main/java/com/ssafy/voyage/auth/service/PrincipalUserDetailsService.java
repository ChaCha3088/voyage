package com.ssafy.voyage.auth.service;

import com.ssafy.voyage.auth.PrincipalUserDetails;
import com.ssafy.voyage.entity.Member;
import com.ssafy.voyage.message.MessageMaker;
import com.ssafy.voyage.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.ssafy.voyage.message.MemberMessages.MEMBER;
import static com.ssafy.voyage.message.message.Messages.NOT_EXISTS;
import static com.ssafy.voyage.message.message.Messages.SUCH;

@Service
@RequiredArgsConstructor
public class PrincipalUserDetailsService implements UserDetailsService {
    private final MemberRepository memberRepository;

    //이런 요청이 들어왔는데, 얘 혹시 회원이야?
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        //Member를 찾는다.
        Member member = memberRepository.findNotDeletedByEmail(email)
                // 없으면, UsernameNotFoundException 발생
                .orElseThrow(() -> new UsernameNotFoundException(MessageMaker.getMessageMaker().add(SUCH).add(MEMBER).add(NOT_EXISTS).toString()));

        //있으면, PrincipalUserDetails 생성
        return new PrincipalUserDetails(member);
    }
}

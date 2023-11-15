package com.ssafy.voyage.service;

import com.ssafy.voyage.dto.member.MemberCreationDto;
import com.ssafy.voyage.dto.member.MemberUpdateDto;
import com.ssafy.voyage.entity.Member;
import com.ssafy.voyage.exception.NoSuchMemberException;
import com.ssafy.voyage.repository.MemberRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.ssafy.voyage.message.message.MemberMessages.MEMBER;
import static com.ssafy.voyage.message.message.Messages.NOT_EXISTS;
import static com.ssafy.voyage.message.message.Messages.SUCH;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public Member findMemberById(long memberId) throws NoSuchMemberException {
        return getMemberById(memberId);
    }

    public Member findMemberByEmail(String email) throws NoSuchMemberException {
        return getMemberByEmail(email);
    }

    @Transactional
    public long createMember(MemberCreationDto memberCreationDto) throws DataIntegrityViolationException {
        Member newMember = Member.builder()
            .email(memberCreationDto.getEmail())
            .password(passwordEncoder.encode(memberCreationDto.getPassword()))
            .name(memberCreationDto.getName())
            .build();

        return memberRepository.save(newMember).getId();
    }

    @Transactional
    public long updateMember(MemberUpdateDto memberUpdateDto, String email) throws NoSuchMemberException {
        Member member = getMemberByEmail(email);

        member.changePassword(memberUpdateDto.getPassword());

        return member.getId();
    }

    @Transactional
    public void deleteMember(String email) throws NoSuchMemberException {
        Member member = getMemberByEmail(email);

        memberRepository.delete(member);
    }

    private Member getMemberById(long memberId) throws NoSuchMemberException {
        return memberRepository.findById(memberId)
            .orElseThrow(() -> new NoSuchMemberException(new StringBuffer().append(SUCH.getMessage()).append(MEMBER).append(NOT_EXISTS.getMessage()).toString()));
    }

    private Member getMemberByEmail(String email) {
        return memberRepository.findNotDeletedByEmail(email)
            .orElseThrow(() -> new NoSuchMemberException(new StringBuffer().append(SUCH.getMessage()).append(MEMBER).append(NOT_EXISTS.getMessage()).toString()));
    }
}

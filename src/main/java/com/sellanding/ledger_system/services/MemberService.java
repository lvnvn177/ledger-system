package com.sellanding.ledger_system.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sellanding.ledger_system.domain.Member;
import com.sellanding.ledger_system.repositories.MemberRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class MemberService {
    
    @Autowired
    MemberRepository memberRepository;

    /**
     * 회원가입
     */
    public Long join (Member member) {
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember (Member member) {
        List<Member> findMembers = 
            memberRepository.findByName(member.getName());
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    /**
     * 전체 회원 조회
     */
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Optional<Member> findOne(Long memberId) {
        return memberRepository.findById(memberId);
    }


}

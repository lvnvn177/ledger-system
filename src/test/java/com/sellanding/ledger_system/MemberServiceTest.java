package com.sellanding.ledger_system;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.sellanding.ledger_system.domain.Member;
import com.sellanding.ledger_system.repositories.MemberRepository;
import com.sellanding.ledger_system.services.MemberService;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;


@SpringBootTest
@Transactional
public class MemberServiceTest {
   
    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;
    @Autowired EntityManager em; 

    @Test
    public void 회원가입() throws Exception {
        
        //Given
        Member member = new Member(null, 1000);
        member.changeName("kim");
        
        
        //When
        Long saveId = memberService.join(member);
        assertEquals(member, memberRepository.findById(saveId).get());
    }

    @Test
    public void 중복_회원_예외() throws Exception {
        
        //Given
        Member member1 = new Member(null, 0);
        member1.changeName("kim");

        Member member2 = new Member(null, 0);
        member2.changeName("kim");

        //When
        memberService.join(member1);
        em.flush();
        
        // Then
        assertThrows(IllegalStateException.class, () -> {
            memberService.join(member2);
        });

    }
}

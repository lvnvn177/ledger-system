package com.sellanding.ledger_system.repositories;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sellanding.ledger_system.domain.Member;


public interface MemberRepository extends JpaRepository<Member, Long>{
    List<Member> findByName(String name);
}

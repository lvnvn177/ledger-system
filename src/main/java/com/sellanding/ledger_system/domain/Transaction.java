package com.sellanding.ledger_system.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.sellanding.ledger_system.domain.enums.TransactionStatus;

import jakarta.persistence.*;

@Entity
@Table(name = "ORDERS")
public class Transaction {
    
    @Id @GeneratedValue
    @Column(name = "ORDER_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member; // 거래 회원

    @OneToMany(mappedBy = "transaction", cascade = CascadeType.ALL)
    private List<Position> positions = new ArrayList<Position>();

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "LEDGER_ID")
    private Ledger ledger; // 거래 정보 

    private Date TransactionDate; // 거래 시간 

    @Enumerated(EnumType.STRING)
    private TransactionStatus transactionStatus; // 거래 상태 

    /**
     * 연관 관계 메소드
     */
    public void setMember(Member member) {
        this.member = member;
    }

    

    

    
}

package com.sellanding.ledger_system.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.sellanding.ledger_system.domain.enums.TransactionStatus;

import jakarta.persistence.*;

@Entity
@Table(name = "Transactions")
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

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "TRANSACTION_DATE", nullable = false)
    private Date TransactionDate; // 거래 시간 

    @Enumerated(EnumType.STRING)
    @Column(name = "TRANSACTION_STATUS", nullable = false)
    private TransactionStatus transactionStatus; // 거래 상태 

    protected Transaction() {}
    /**
     * 연관 관계 메소드
     */


    public void setMember(Member member) {
        this.member = member;
    }
    
    public void complete() {
        if (transactionStatus != TransactionStatus.PENDING) {
            throw new IllegalStateException("이미 완료되었거나 취소된 거래입니다");
        }
        this.transactionStatus = TransactionStatus.COMPLETED;
    }
}

package com.sellanding.ledger_system.domain;

import java.time.LocalDateTime;
import java.util.List;

import com.sellanding.ledger_system.domain.enums.TransactionStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GenerationType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;


/**
 * 고객 거래 기록
 */
@Entity
@Table(name = "transaction_history")
public class TransactionHistory {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(nullable = false)
    @OneToMany
    private List<Order> orders;
    
    @Column(nullable = false)
    private TransactionStatus status;

    @Column(name = "translation_time", nullable = false)
    private LocalDateTime translationTime;
}

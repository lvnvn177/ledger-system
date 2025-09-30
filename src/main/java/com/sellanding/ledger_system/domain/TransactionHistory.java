package com.sellanding.ledger_system.domain;

import java.time.LocalDateTime;
import java.util.List;

import com.sellanding.ledger_system.domain.enums.TransactionStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GenerationType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;


/**
 * 
 */
@Entity
public class TransactionHistory {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(nullable = false)
    private List<Order> orders;
    
    @Column(nullable = false)
    private TransactionStatus status;

    @Column(name = "translation_time", nullable = false)
    private LocalDateTime translationTime;
}

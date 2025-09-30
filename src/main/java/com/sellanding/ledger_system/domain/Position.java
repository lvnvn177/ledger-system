package com.sellanding.ledger_system.domain;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;


/**
 * 유저의 구매 포지션
 * 
 */
@Entity
public class Position {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_account_id", nullable = false)
    private UserAccount userAccount;

    @Column(name = "asset_id", nullable = false)
    private String assetId; // 주식 고유 코드 
    @Column(name = "quantity", nullable = false)
    private Long quantity; // 보유 수량 
    @Column(name = "avg_buy_price", nullable = false)
    private BigDecimal avgBuyPrice;  // 평균 매입가 
   
}

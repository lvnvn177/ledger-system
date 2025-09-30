package com.sellanding.ledger_system.domain;

import java.math.BigDecimal;

import com.sellanding.ledger_system.domain.enums.AssetTicker;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;


/**
 * 유저의 구매 자산
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

    @Column(name = "asset_ticker", nullable = false)
    private AssetTicker assetTicker; // ETF 고유 티커  

    @Column(name = "quantity", nullable = false)
    private Long quantity; // 보유 수량 

    @Column(name = "avg_buy_price", nullable = false)
    private BigDecimal avgBuyPrice;  // 평균 매입가 
   
}

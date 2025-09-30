package com.sellanding.ledger_system.domain;

import java.time.LocalDateTime;

import com.sellanding.ledger_system.domain.enums.AssetTicker;
import com.sellanding.ledger_system.domain.enums.OrderStatus;
import com.sellanding.ledger_system.domain.enums.OrderType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;



/**
 *  주문 요청 정보 
 */
@Entity
public class Order {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "order_type", nullable = false)
    private OrderType orderType; // Buy or Sell

    @Column(name = "order_ticker", nullable = false)
    private AssetTicker orderTicker; // 주문한 ETF 고유 티커

    @Column(nullable = false)
    private Long quantity; // 주문 수량 

    @Column(nullable = false)
    private OrderStatus status; // PENDING, COMPLETED, FAILED 

    @Column(name = "order_time" ,nullable = false)
    private LocalDateTime orderTime; // 주문 시간 
}

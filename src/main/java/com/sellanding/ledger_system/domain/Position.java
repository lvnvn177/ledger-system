package com.sellanding.ledger_system.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "ORDER_ITEM")
public class Position {
    
    @Id @GeneratedValue
    @Column(name = "POSITION_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ASSET_ID")
    private Asset asset; // 거래 종목 

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORDER_ID")
    private Order order; // 거래

    private float orderPrice; // 거래 가격
    private float quantity; // 거래 수량 
    
    
}

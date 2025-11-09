package com.sellanding.ledger_system.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "POSITION")
public class Position {
    
    @Id @GeneratedValue
    @Column(name = "POSITION_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ASSET_ID")
    private Asset asset; // 거래 종목 

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TRANSACTION_ID")
    private Transaction transaction; // 거래
    
    protected Position() {}

    public Position(Position position) {
        this.asset = position.asset;
        this.transaction = position.transaction;
    }

    public Position getPosition() {
        return this;
    }
}

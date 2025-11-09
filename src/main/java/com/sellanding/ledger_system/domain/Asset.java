package com.sellanding.ledger_system.domain;

import com.sellanding.ledger_system.domain.enums.Ticker;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "DTYPE")
public abstract class Asset {
    
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ASSET_ID")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "TICKER", nullable = false)
    private Ticker ticker;
    
    @Column(name = "POSITION_PRICE")
    private float price;
    
    @Column(name = "POSITION_QUANTITY")
    private float quantity;

}

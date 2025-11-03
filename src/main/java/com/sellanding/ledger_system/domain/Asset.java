package com.sellanding.ledger_system.domain;

import com.sellanding.ledger_system.domain.enums.Ticker;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "DTYPE")
public abstract class Asset {
    
    @Id @GeneratedValue
    @Column(name = "ASSET_ID")
    private Long id;

    private Ticker ticker;
    private float price;
    private float quantity;

}

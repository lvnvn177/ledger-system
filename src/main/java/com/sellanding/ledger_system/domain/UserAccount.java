package com.sellanding.ledger_system.domain;

import java.math.BigDecimal;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class UserAccount {

    @Id
    @GeneratedValue(strategy =  GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, name = "name")
    private String username;

    @Column(nullable = false, name = "balance")
    private BigDecimal balance;

    @Column(name = "portifolio")
    private List<LedgerEntry> portifolio;
}

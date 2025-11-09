package com.sellanding.ledger_system.domain;

import java.util.List;
import java.util.stream.Collectors;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class Portfolio {


    @Id @GeneratedValue
    @Column(name = "PORTFOLIO_ID")
    private Long id;

    @OneToOne(mappedBy = "portfolio")
    private Member member;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Position> portfolio;

    public List<Position> getPortfolio() { 
       return portfolio.stream()
                .map(position -> new Position(position.getPosition()))
                .collect(Collectors.toUnmodifiableList());
    }

    public void addPosition(Position position) {
        this.portfolio.add(position);
    }
}

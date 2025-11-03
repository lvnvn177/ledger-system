package com.sellanding.ledger_system.domain;

import java.util.List;
import java.util.stream.Collectors;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

public class Portfolio {


    @Id @GeneratedValue
    @Column(name = "PORTFOLIO_ID")
    private Long id;

    @OneToOne(mappedBy = "portfolio")
    private Member member;

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

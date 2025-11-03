package com.sellanding.ledger_system.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Member {
    
    @Id @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;

    private String name;

    private Integer balance;

    @OneToMany(mappedBy = "member")
    private List<Position> portfolio = new ArrayList<Position>();

    public Member(String name, int initialPrice) {
        this.name = name;
        this.balance = initialPrice;
    }

    public Long getId() { return id; }

    public String getName() { return name; }

    public int getBalance() { return balance; }

    public List<Position> getPortfolio() { 
       return portfolio.stream()
                .map(position -> new Position(position.getPosition()))
                .collect(Collectors.toUnmodifiableList());
    }

    public void changeName(String newName) {
        this.name = newName;
    }

    public void deposit(int amount) {
        if(amount <=0 ) throw new IllegalArgumentException("입금액은 0보다 커야 합니다.");
        this.balance += amount;
    }

    public void withdraw(int amount) {
        if (amount <= 0) throw new IllegalArgumentException("출금액은 0보다 커야 합니다.");
        if (this.balance < amount) throw new IllegalStateException("잔액 부족");
        this.balance -= amount;
    }

    public void addPosition(Position position) {
        this.portfolio.add(position);
    }
}

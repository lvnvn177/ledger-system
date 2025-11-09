package com.sellanding.ledger_system.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity
public class Member {
    
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MEMBER_ID")
    private Long id;

    private String name;

    private Integer balance;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "PORTFOLIO_ID")
    private Portfolio portfolio;

    public Member(String name, int initialPrice) {
        this.name = name;
        this.balance = initialPrice;
    }

    /**
     * getter
     */
    public Long getId() { return id; }

    public String getName() { return name; }

    public int getBalance() { return balance; }

    /**
     * setter
     */
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
}

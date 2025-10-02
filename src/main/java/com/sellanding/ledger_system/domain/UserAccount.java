package com.sellanding.ledger_system.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

/**
 * 고객 계정 정보를 나타내는 엔티티
 * 사용자 정보, 잔액, 포트폴리오를 관리
 */
@Entity
@Table(name = "user_account")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(nullable = false, unique = true, name = "username")
    private String username;

    @Column(nullable = false, name = "balance")
    private BigDecimal balance;

    @OneToMany(mappedBy = "userAccount", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
private List<Position> portfolio = new ArrayList<>();

    @Builder
    public UserAccount(String username, BigDecimal balance) {
        this.username = username;
        this.balance = balance;
    }

    //== 조회 메서드 ==//
    public Long getId() {
        return id;
    }
    
    public String getUsername() {
        return username;
    }

    public BigDecimal getBalance() {
        return this.balance;
    }

    public List<Position> getPortfolio() {
        return portfolio;
    }

    //== 연관관계 편의 메서드 ==//
    public void addPortfolio(Position position) {
        portfolio.add(position);
        position.setUserAccount(this);
    }

    //== 비즈니스 로직 ==//
    /**
     * 계좌 잔액을 특정 값으로 설정합니다.
     * 이 메서드는 명시적인 잔액 설정이 필요할 때 사용됩니다.
     * @param balance 새로운 잔액
     */
    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    /**
     * 계좌 잔액을 업데이트합니다.
     * @param amount 변경할 금액 (양수: 입금, 음수: 출금)
     */
    public void updateBalance(BigDecimal amount) {
        this.balance = this.balance.add(amount);
    }
}

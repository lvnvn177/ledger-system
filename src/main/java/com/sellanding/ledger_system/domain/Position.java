package com.sellanding.ledger_system.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.sellanding.ledger_system.domain.enums.AssetTicker;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;


/**
 * 고객이 보유한 특정 자산
 * 
 */
@Entity
@Table(name = "position")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Position {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_account_id", nullable = false)
    private UserAccount userAccount;

    @Column(name = "asset_ticker", nullable = false)
    private AssetTicker assetTicker; // ETF 고유 티커  

    @Column(name = "quantity", nullable = false)
    private Long quantity; // 보유 수량 

    @Column(name = "avg_buy_price", nullable = false)
    private BigDecimal avgBuyPrice;  // 평균 매수가 

    @Builder
    public Position(UserAccount userAccount, AssetTicker assetTicker, Long quantity, BigDecimal avgBuyPrice) {
        this.userAccount = userAccount;
        this.assetTicker = assetTicker;
        this.quantity = quantity;
        this.avgBuyPrice = avgBuyPrice;
    }

    public Long getId() {
        return id;
    }

    public UserAccount getUserAccount() {
        return userAccount;
    }

    public AssetTicker getAssetTicker() {
        return assetTicker;
    }

    public Long getQuantity() {
        return quantity;
    }

    public BigDecimal getAvgBuyPrice() {
        return avgBuyPrice;
    }

    //== 연관관계 편의 메서드 ==//
    /**
     * UserAccount와의 연관관계를 설정합니다.
     * 이 메서드는 UserAccount의 addPortfolio 메서드 내부에서 호출되어야 합니다.
     * @param userAccount 연관될 사용자 계정
     */
    public void setUserAccount(UserAccount userAccount) {
        this.userAccount = userAccount;
    }

      //== 비즈니스 로직 ==//
    /**
     * 포지션에 수량을 추가하고 평균 매입가를 재계산합니다. (매수)
     * @param newQuantity 추가 수량
     * @param newPrice 추가 매입 가격
     */
    public void addQuantity(Long newQuantity, BigDecimal newPrice) {
        BigDecimal totalCost = this.avgBuyPrice.multiply(BigDecimal.valueOf(this.quantity));
        BigDecimal newTotalCost = newPrice.multiply(BigDecimal.valueOf(newQuantity));

        this.quantity += newQuantity;
        this.avgBuyPrice = (totalCost.add(newTotalCost).divide(BigDecimal.valueOf(this.quantity), 
                    2, RoundingMode.HALF_UP));
        
    }

    /**
     * 포지션에서 수량을 감소시킵니다. (매도)
     * @param soldQuantity 매도 수량
     */
    public void subtractQuantity(Long soldQuantity) {
        if (this.quantity < soldQuantity) {
            throw new IllegalArgumentException("보유 수량보다 많이 매도할 수 없습니다.");
        }
        this.quantity -= soldQuantity;
    }
   
}

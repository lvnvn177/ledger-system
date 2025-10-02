package com.sellanding.ledger_system.domain;

import com.sellanding.ledger_system.domain.enums.AssetTicker;
import com.sellanding.ledger_system.domain.enums.OrderSide;
import com.sellanding.ledger_system.domain.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 거래 주문 정보를 나타내는 엔티티
 */
@Getter
@Entity
@Table(name = "orders") // 데이터베이스 예약어와 충돌을 피하기 위해 "orders"로 변경
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_account_id", nullable = false)
    private UserAccount userAccount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AssetTicker assetTicker;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderSide side;

    @Column(nullable = false)
    private Long quantity;

    @Column(nullable = false)
    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    @Builder
    public Order(UserAccount userAccount, AssetTicker assetTicker, OrderSide side, Long quantity, BigDecimal price, OrderStatus status, LocalDateTime timestamp) {
        this.userAccount = userAccount;
        this.assetTicker = assetTicker;
        this.side = side;
        this.quantity = quantity;
        this.price = price;
        this.status = status;
        this.timestamp = timestamp;
    }

    //== 비즈니스 로직 ==//
    /**
     * 주문 상태를 변경합니다.
     * @param newStatus 새로운 주문 상태
     */
    public void updateStatus(OrderStatus newStatus) {
        this.status = newStatus;
    }
}

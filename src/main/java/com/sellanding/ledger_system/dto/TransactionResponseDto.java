package com.sellanding.ledger_system.dto;

import com.sellanding.ledger_system.domain.Order;
import com.sellanding.ledger_system.domain.enums.OrderStatus;

import java.time.LocalDateTime;


public final class TransactionResponseDto {

    private final Long orderId;
    private final OrderStatus status;
    private final LocalDateTime transactionDate;

    
    private TransactionResponseDto(Long orderId, OrderStatus status, LocalDateTime transactionDate) {
        this.orderId = orderId;
        this.status = status;
        this.transactionDate = transactionDate;
    }

    public static TransactionResponseDto from(Order order) {
        return new TransactionResponseDto(
            order.getId(),
            order.getStatus(),
            order.getTimestamp()
        );
    }

    public Long getOrderId() { return orderId; }
    public OrderStatus getStatus() { return status; }
    public LocalDateTime getTransactionDate() { return transactionDate; }
}

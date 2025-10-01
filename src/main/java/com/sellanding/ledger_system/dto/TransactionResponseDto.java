package com.sellanding.ledger_system.dto;

import com.sellanding.ledger_system.domain.Order;
import com.sellanding.ledger_system.domain.enums.OrderStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class TransactionResponseDto {

    private final Long orderId;
    private final OrderStatus status;
    private final LocalDateTime transactionDate;

    @Builder
    public TransactionResponseDto(Long orderId, OrderStatus status, LocalDateTime transactionDate) {
        this.orderId = orderId;
        this.status = status;
        this.transactionDate = transactionDate;
    }

    public static TransactionResponseDto from(Order order) {
        return TransactionResponseDto.builder()
                .orderId(order.getId())
                .status(order.getStatus())
                .transactionDate(order.getTimestamp())
                .build();
    }
}

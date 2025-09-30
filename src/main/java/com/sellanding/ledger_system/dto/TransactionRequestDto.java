package com.sellanding.ledger_system.dto;

import java.math.BigDecimal;

import com.sellanding.ledger_system.domain.enums.OrderType;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TransactionRequestDto {
    private OrderType orderType;
    private BigDecimal price;
    private Long quantity;
}

package com.sellanding.ledger_system.dto;

import com.sellanding.ledger_system.domain.enums.AssetTicker;
import com.sellanding.ledger_system.domain.enums.OrderSide;
import com.sellanding.ledger_system.domain.enums.OrderType;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class TransactionRequestDto {

    private Long userId;
    private AssetTicker assetTicker;
    private OrderType orderType;
    private OrderSide side;
    private Long quantity;
    private BigDecimal price;
}

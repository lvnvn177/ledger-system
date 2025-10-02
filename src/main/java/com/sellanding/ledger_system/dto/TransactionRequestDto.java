package com.sellanding.ledger_system.dto;

import com.sellanding.ledger_system.domain.enums.AssetTicker;
import com.sellanding.ledger_system.domain.enums.OrderSide;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public final class TransactionRequestDto {

    @NotNull(message = "사용자 ID는 필수입니다.")
    private Long userId;

    @NotNull(message = "자산 티커는 필수입니다.")
    private AssetTicker assetTicker;

    @NotNull(message = "거래 방향은 필수입니다.")
    private OrderSide side;

    @NotNull(message = "수량은 필수입니다.")
    private Long quantity;

    @NotNull(message = "가격은 필수입니다.")
    @Positive(message = "가격은 양수여야 합니다.")
    private BigDecimal price;

    private TransactionRequestDto() {}

    public Long getUserId() { return userId; }
    public AssetTicker getAssetTicker() { return assetTicker; }
    public OrderSide getSide() { return side; }
    public Long getQuantity() { return quantity; }
    public BigDecimal getPrice() { return price; }
}

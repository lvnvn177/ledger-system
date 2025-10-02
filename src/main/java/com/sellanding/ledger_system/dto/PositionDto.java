package com.sellanding.ledger_system.dto;

import java.math.BigDecimal;

import com.sellanding.ledger_system.domain.Position;
import com.sellanding.ledger_system.domain.enums.AssetTicker;

public class PositionDto {
    private final AssetTicker assetTicker;
    private final Long quantity;
    private final BigDecimal averageBuyPrice;

    private PositionDto(AssetTicker assetTicker, Long quantity, BigDecimal averageBuyPrice) {
        this.assetTicker = assetTicker;
        this.quantity = quantity;
        this.averageBuyPrice = averageBuyPrice;
    }

    public static PositionDto from(Position position) {
        return new PositionDto(
            position.getAssetTicker(),
            position.getQuantity(),
            position.getAvgBuyPrice()
        );
    }

    public AssetTicker getAssetTicker() { return assetTicker;}
    public Long getQuantity() { return quantity; }
    public BigDecimal getAverageBuyPrice() { return averageBuyPrice;}
}

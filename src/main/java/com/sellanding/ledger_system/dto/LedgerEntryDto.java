package com.sellanding.ledger_system.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.sellanding.ledger_system.domain.LedgerEntry;
import com.sellanding.ledger_system.domain.enums.AssetTicker;
import com.sellanding.ledger_system.domain.enums.LedgerEntryType;

public class LedgerEntryDto {

    private final String entryId;
    private final LedgerEntryType type;
    private final AssetTicker assetTicker;
    private final BigDecimal amount;
    private final LocalDateTime timestamp;

    private LedgerEntryDto(String entryId, LedgerEntryType type, 
    AssetTicker assetTicker, BigDecimal amount, LocalDateTime timestamp) 
    {
        this.entryId = entryId;
        this.type = type;
        this.assetTicker = assetTicker;
        this.amount = amount;
        this.timestamp = timestamp;
    }

    public static LedgerEntryDto from(LedgerEntry ledgerEntry) {
        return new LedgerEntryDto(
            ledgerEntry.getEntryId(),
            ledgerEntry.getType(),
            ledgerEntry.getAssetTicker(),
            ledgerEntry.getAmount(),
            ledgerEntry.getTimestamp()
        );
    }


    public String getEntryId() { return entryId; }
    public LedgerEntryType getType() { return type; }
    public AssetTicker getAssetTicker() { return assetTicker; }
    public BigDecimal getAmount() { return amount; }
    public LocalDateTime getTimestamp() { return timestamp; }
}

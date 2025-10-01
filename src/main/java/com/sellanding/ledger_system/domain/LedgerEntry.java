package com.sellanding.ledger_system.domain;

import com.sellanding.ledger_system.domain.enums.AssetTicker;
import com.sellanding.ledger_system.domain.enums.LedgerEntryType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 원장 항목을 나타내는 엔티티
 */
@Getter
@Entity
@Table(name = "ledger_entry")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LedgerEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String entryId;

    @Column(nullable = false)
    private String userId;

    private String orderId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LedgerEntryType type;

    @Enumerated(EnumType.STRING)
    private AssetTicker assetTicker;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    @Builder
    public LedgerEntry(String entryId, String userId, String orderId, LedgerEntryType type, AssetTicker assetTicker, BigDecimal amount, LocalDateTime timestamp) {
        this.entryId = entryId;
        this.userId = userId;
        this.orderId = orderId;
        this.type = type;
        this.assetTicker = assetTicker;
        this.amount = amount;
        this.timestamp = timestamp;
    }
}
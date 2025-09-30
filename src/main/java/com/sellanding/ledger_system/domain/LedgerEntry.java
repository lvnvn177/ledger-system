package com.sellanding.ledger_system.domain;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

import com.sellanding.ledger_system.domain.enums.LedgerEntryType;


@Entity
@Table(name = "ledger_entries")
public class LedgerEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String entryId;

    @Column(nullable = false)
    private String userId;

    @Column(nullable = false)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LedgerEntryType type;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    @Column(length = 500)
    private String description;

    @Column(nullable = false)
    private String referenceId;

    @Column(nullable = false)
    private BigDecimal balanceAfter;

    @Column
    private String assetSymbol;

    @Column
    private Long assetQuantity;

    @Column
    private BigDecimal assetPrice;

    @Column(nullable = false)
    private boolean verified;

    @Version
    private Long version;

    // JPA를 위한 기본 생성자
    protected LedgerEntry() {
    }

    // 빌더 패턴을 위한 시작점
    public static LedgerEntryBuilder builder() {
        return new LedgerEntryBuilder();
    }

    // Getter 메서드들
    public Long getId() {
        return id;
    }

    public String getEntryId() {
        return entryId;
    }

    public String getUserId() {
        return userId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public LedgerEntryType getType() {
        return type;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getDescription() {
        return description;
    }

    public String getReferenceId() {
        return referenceId;
    }

    public BigDecimal getBalanceAfter() {
        return balanceAfter;
    }

    public String getAssetSymbol() {
        return assetSymbol;
    }

    public Long getAssetQuantity() {
        return assetQuantity;
    }

    public BigDecimal getAssetPrice() {
        return assetPrice;
    }

    public boolean isVerified() {
        return verified;
    }

    public Long getVersion() {
        return version;
    }

    // LedgerEntry 빌더 클래스
        // LedgerEntry 빌더 클래스
    public static class LedgerEntryBuilder {
        private final LedgerEntry entry;

        private LedgerEntryBuilder() {
            entry = new LedgerEntry();
            entry.entryId = UUID.randomUUID().toString();
            entry.timestamp = LocalDateTime.now();
            entry.verified = false;
        }

        public LedgerEntryBuilder userId(String userId) {
            entry.userId = userId;
            return this;
        }

        public LedgerEntryBuilder amount(BigDecimal amount) {
            entry.amount = amount;
            return this;
        }

        public LedgerEntryBuilder type(LedgerEntryType type) {
            entry.type = type;
            return this;
        }

        public LedgerEntryBuilder description(String description) {
            entry.description = description;
            return this;
        }

        public LedgerEntryBuilder referenceId(String referenceId) {
            entry.referenceId = referenceId;
            return this;
        }

        public LedgerEntryBuilder balanceAfter(BigDecimal balanceAfter) {
            entry.balanceAfter = balanceAfter;
            return this;
        }

        public LedgerEntryBuilder assetSymbol(String assetSymbol) {
            entry.assetSymbol = assetSymbol;
            return this;
        }

        public LedgerEntryBuilder assetQuantity(Long assetQuantity) {
            entry.assetQuantity = assetQuantity;
            return this;
        }

        public LedgerEntryBuilder assetPrice(BigDecimal assetPrice) {
            entry.assetPrice = assetPrice;
            return this;
        }

        public LedgerEntryBuilder verified(boolean verified) {
            entry.verified = verified;
            return this;
        }

        public LedgerEntryBuilder timestamp(LocalDateTime timestamp) {
            entry.timestamp = timestamp;
            return this;
        }

        public LedgerEntry build() {
            // 필수 필드 검증
            if (entry.userId == null) {
                throw new IllegalStateException("User ID cannot be null");
            }
            if (entry.amount == null) {
                throw new IllegalStateException("Amount cannot be null");
            }
            if (entry.type == null) {
                throw new IllegalStateException("Type cannot be null");
            }
            if (entry.referenceId == null) {
                throw new IllegalStateException("Reference ID cannot be null");
            }
            if (entry.balanceAfter == null) {
                throw new IllegalStateException("Balance after cannot be null");
            }
            
            // 특정 타입일 때 필수 필드 검증
            if ((entry.type == LedgerEntryType.TRADE_BUY || entry.type == LedgerEntryType.TRADE_SELL) 
                    && entry.assetSymbol == null) {
                throw new IllegalStateException("Asset symbol is required for trade entries");
            }

            return entry;
        }
    }

    @Override
    public String toString() {
        return "LedgerEntry{" +
                "id=" + id +
                ", entryId='" + entryId + '\'' +
                ", userId='" + userId + '\'' +
                ", amount=" + amount +
                ", type=" + type +
                ", timestamp=" + timestamp +
                ", referenceId='" + referenceId + '\'' +
                ", balanceAfter=" + balanceAfter +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LedgerEntry that = (LedgerEntry) o;
        return entryId.equals(that.entryId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(entryId);
    }
}
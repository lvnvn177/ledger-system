package com.sellanding.ledger_system.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public final class CashTransactionDto {
    
    private CashTransactionDto() {}


    public static final class Request {
        @NotNull(message = "금액은 필수입니다.")
        @Positive(message = "금액은 0보다 커야 합니다.")
        private BigDecimal amount;

        private Request() {}

        public BigDecimal getAmount() {
            return amount;
        }
    }

    public static final class Response {
        private final Long userId;
        private final BigDecimal newBalance;
        private final String transactionType;

        public Response(Long userId, BigDecimal newBalance, String transactionType) {
            this.userId = userId;
            this.newBalance = newBalance;
            this.transactionType = transactionType;
        }

        public Long getUserId() { return userId; }
        public BigDecimal getNewBalance() { return newBalance; }
        public String getTransactionType() { return transactionType;
    }
   } 
}

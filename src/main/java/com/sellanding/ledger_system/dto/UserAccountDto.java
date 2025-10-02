package com.sellanding.ledger_system.dto;

import java.math.BigDecimal;

import com.sellanding.ledger_system.domain.UserAccount;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public class UserAccountDto {
    
    private UserAccountDto() {}

    public static class Create {
        @NotBlank(message = "사용자 ID는 필수입니다.")
        private String username;
        
        @NotNull(message = "초기 잔액은 필수입니다.")
        @PositiveOrZero(message = "초기 잔액은 0 이상이어야 합니다.")
        private BigDecimal initialBalance;

        private Create() {}

        public UserAccount toEntity() {
            return UserAccount.builder()
                    .username(this.username)
                    .balance(this.initialBalance)
                    .build();
        }

        public String getUsername() {
            return username;
        }

        public BigDecimal getInitialBalance() {
            return initialBalance;
        }
    }
}

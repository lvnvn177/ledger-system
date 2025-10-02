package com.sellanding.ledger_system.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import com.sellanding.ledger_system.domain.UserAccount;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public final class UserAccountDto {
    
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

    public static class Response {
        private final Long userId;
        private final String username;
        private final BigDecimal balance;
        private final List<PositionDto> portfolio;

        private Response(Long userId, String username, BigDecimal balance, List<PositionDto> portfolio) {
            this.userId = userId;
            this.username = username;
            this.balance = balance;
            this.portfolio = portfolio;
        }

        public static Response from(UserAccount userAccount) {
            List<PositionDto> positionDtos = userAccount.getPortfolios().stream()
                .map(PositionDto::from)
                .collect(Collectors.toList());
            return new Response(
                    userAccount.getId(),
                    userAccount.getUsername(),
                    userAccount.getBalance(),
                    positionDtos
            );
        }
        
        public Long getUserId() { return userId; }
        public String getUsername() { return username; }
        public BigDecimal getBalance() { return balance; }
        public List<PositionDto> getPortfolio() { return portfolio; }
    }
}

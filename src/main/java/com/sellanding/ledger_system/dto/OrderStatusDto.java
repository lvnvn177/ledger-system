package com.sellanding.ledger_system.dto;

import com.sellanding.ledger_system.domain.enums.OrderStatus;

import jakarta.validation.constraints.NotNull;

public final class OrderStatusDto {
    
    private OrderStatusDto() {}

    /**
     * 주문 상태 변경을 요청할 때 사용하는 DTO
     * (예: 주문 취소)
     */
    public static final class Request {
        @NotNull(message = "변경할 주문 상태는 필수입니다.")
        private OrderStatus status;

        private Request() {}

        public OrderStatus getStatus() {
            return status;
        }
    }

    /**
     * 특정 주문의 현재 상태를 응답할 때 사용하는 DTO
     */
    public static final class Response {
        private final Long orderId;
        private final OrderStatus status;

        private Response(Long orderId, OrderStatus status) {
            this.orderId = orderId;
            this.status = status;
        }

        public Long getOrderId() {
            return orderId;
        }

        public OrderStatus getStatus() {
            return status;
        }
    }
    
}

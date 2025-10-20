package com.sellanding.ledger_system.domain.enums;


/**
 * 주문 상태 
 */
public enum OrderStatus {
    PENDING,        // 대기 중
    COMPLETED,      // 체결 완료
    CANCELED,       // 취소됨
    REJECTED        // 거부됨
}

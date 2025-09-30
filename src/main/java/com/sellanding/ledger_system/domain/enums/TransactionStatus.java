package com.sellanding.ledger_system.domain.enums;

/**
 * 거래 상태 
 */
public enum TransactionStatus {
    INITIATED,      // 시작됨
    PROCESSING,     // 처리 중
    COMPLETED,      // 완료
    FAILED          // 실패
}

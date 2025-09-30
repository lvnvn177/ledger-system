package com.sellanding.ledger_system.domain.enums;


/**
 * 원장 시스템 내에서 발생하는 모든 이벤트 유형을 정의하는 열거형 클래스
 * 이벤트 소싱 패턴에서 상태 변경을 추적하는 데 사용 
 */
public enum EventType {
    // 사용자 계정 관련 이벤트
    USER_CREATED,           // 사용자 계정 생성
    USER_UPDATED,           // 사용자 정보 업데이트
    USER_DELETED,           // 사용자 계정 삭제
    
    // 잔고 변경 관련 이벤트
    BALANCE_DEPOSITED,      // 입금 완료
    BALANCE_WITHDRAWN,      // 출금 완료
    BALANCE_ADJUSTED,       // 잔고 조정 (관리자 조정 등)
    
    // 주문 관련 이벤트
    ORDER_CREATED,          // 주문 생성
    ORDER_UPDATED,          // 주문 수정
    ORDER_CANCELED,         // 주문 취소
    ORDER_EXECUTED,         // 주문 체결
    ORDER_PARTIALLY_EXECUTED, // 주문 부분 체결
    ORDER_REJECTED,         // 주문 거부
    
    // 자산 관련 이벤트
    ASSET_ACQUIRED,         // 자산 취득
    ASSET_SOLD,             // 자산 매도
    ASSET_TRANSFERRED,      // 자산 이체
    
    // 포트폴리오 관련 이벤트
    PORTFOLIO_UPDATED,      // 포트폴리오 업데이트
    PORTFOLIO_REBALANCED,   // 포트폴리오 리밸런싱
    
    // 시스템 이벤트
    SYSTEM_ERROR,           // 시스템 오류 발생
    AUDIT_LOG_CREATED,      // 감사 로그 생성
    DATA_SNAPSHOT_CREATED,  // 데이터 스냅샷 생성
    
    // 거래 관련 이벤트
    TRANSACTION_STARTED,    // 거래 시작
    TRANSACTION_COMPLETED,  // 거래 완료
    TRANSACTION_FAILED;     // 거래 실패
    
    /**
     * 이벤트가 사용자 관련 이벤트인지 확인
     */
    public boolean isUserEvent() {
        return this == USER_CREATED || this == USER_UPDATED || this == USER_DELETED;
    }

    /**
     * 이벤트가 주문 관련 이벤트인지 확인 
     */
     public boolean isOrderEvent() {
        return this == ORDER_CREATED || this == ORDER_UPDATED || this == ORDER_CANCELED 
                || this == ORDER_EXECUTED || this == ORDER_PARTIALLY_EXECUTED || this == ORDER_REJECTED;
    }

    /**
     * 이벤트가 시스템 이벤트인지 확인 
     */
    public boolean isSystemEvent() {
        return this == SYSTEM_ERROR || this == AUDIT_LOG_CREATED || this == DATA_SNAPSHOT_CREATED;
    }

    /**
     *  이벤트가 잔고 변경 이벤트인지 확인 
     */
    public boolean isBalanceEvent() {
        return this == BALANCE_DEPOSITED || this == BALANCE_WITHDRAWN || this == BALANCE_ADJUSTED;
    }
}

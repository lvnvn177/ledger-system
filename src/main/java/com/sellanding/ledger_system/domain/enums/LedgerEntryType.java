package com.sellanding.ledger_system.domain.enums;

/**
 * 원장 항목 유형
 */
public enum LedgerEntryType {
    DEPOSIT,        // 입금
    WITHDRAWAL,     // 출금
    TRADE_BUY,      // 매수 거래
    TRADE_SELL      // 매도 거래
}
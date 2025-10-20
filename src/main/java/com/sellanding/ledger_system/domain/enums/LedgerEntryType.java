package com.sellanding.ledger_system.domain.enums;

public enum LedgerEntryType {
    DEPOSIT,    // 입금
    WITHDRAWAL, // 출금
    TRADE_BUY,  // 매수 거래
    TRADE_SELL, // 매도 거래
    FEE,        // 수수료
}
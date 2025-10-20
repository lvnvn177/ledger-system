package com.sellanding.ledger_system.util;




public class TestDataGenerator {
    
    /**
     * 요구사항 
     * 1. N명의 사용자가 동시에 특정 주식 종목에 대해 매수/매도 요청을 했을 시 부하 테스트 
     * 2. 만약 해당 종목의 보유 수량이 N-1개 일 때 어떤 사용자가를 '거부됨' 상태로 반환할 지 정한다 
     * 3. 주식 거래를 어떻게 신뢰성 있게 만들지 고민한다 
     */


    /**
     * 사용자 계정 정보 
     * 
     * id: 고유 ID
     * username: 이름 
     * balance: 잔액
     * portfolio: 티커 포트폴리오
     *  */ 


    /**
     * 각 고객당 주문정보 
     * 
     * id: 고유 ID
     * userAccount: 사용자 계좌 객체 
     * assetTicker: 구매 타겟 티커 
     * side: 매수/매도 
     * quantity: 수량
     * price: 가격 
     * status: 거래 상태, 대기 중/체결 완료/취소됨/거부됨
     * timestamp: 거래 요청 일시 
     *  */ 

    /**
     * 주식 항목 (Ticker) 데이터 
     * 
     * 
     */
}

package com.sellanding.ledger_system.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sellanding.ledger_system.dto.OrderStatusDto;
import com.sellanding.ledger_system.dto.TransactionRequestDto;
import com.sellanding.ledger_system.dto.TransactionResponseDto;
import com.sellanding.ledger_system.services.LedgerService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {
    
    private final LedgerService ledgerService;

    /**
     * 주식 거래(매수/매도) 요청 API
     * @param requestDto 거래 요청 정보
     * @return 거래 처리 결과
     */
    @PostMapping
    public ResponseEntity<TransactionResponseDto> executeTransaction(@Valid @RequestBody TransactionRequestDto requestDto) {
        TransactionResponseDto response = ledgerService.processTransaction(requestDto);
        return ResponseEntity.ok(response);
    }

    /**
     * 특정 주문 취소 API
     * @param orderId 취소할 주문 ID
     * @return 취소된 주문의 상태 정보 
     */
    @PostMapping("/{orderId}/cancel")
    public ResponseEntity<OrderStatusDto.Response> cancelOrder(@PathVariable("orderId") Long orderId) {
        OrderStatusDto.Response response = ledgerService.cancelOrder(orderId);
        return ResponseEntity.ok(response);
    }
}

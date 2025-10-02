package com.sellanding.ledger_system.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping
    public ResponseEntity<TransactionResponseDto> executeTransaction(@Valid @RequestBody TransactionRequestDto requestDto) {
        TransactionResponseDto response = ledgerService.processTransaction(requestDto);
        return ResponseEntity.ok(response);
    }
}

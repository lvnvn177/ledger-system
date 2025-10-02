package com.sellanding.ledger_system.controller;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sellanding.ledger_system.dto.CashTransactionDto;
import com.sellanding.ledger_system.dto.LedgerEntryDto;
import com.sellanding.ledger_system.dto.UserAccountDto;
import com.sellanding.ledger_system.services.LedgerService;
import com.sellanding.ledger_system.services.UserAccountService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class UserAccountController {
    
    private final UserAccountService userAccountService;
    private final LedgerService ledgerService;

    @PostMapping
    public ResponseEntity<UserAccountDto.Response> createAccount(@Valid @RequestBody UserAccountDto.Create createDto) {
        UserAccountDto.Response response = userAccountService.createUserAccount(createDto);

        URI location = URI.create(String.format("/api.accounts/%d", response.getUserId()));
        return ResponseEntity.created(location).body(response);
    }

    @GetMapping("/{accountId}")
    public ResponseEntity<UserAccountDto.Response> getAccountDetails(@PathVariable("accountId") Long accountId) {
        UserAccountDto.Response response = userAccountService.getUserAccountDetails(accountId);
        return ResponseEntity.ok(response);
    }


    /**
     * 사용자의 원장 내역을 조회. (선택적으로 기간 필터링 가능)
     * @param accountId 조회할 사용자 ID
     * @param startDate 조회 시작일 
     * @param endDate 조회 종료일 
     * @return 거래 내역 리스트 
     */
    @GetMapping ("/{accountId}/ledger")
        public ResponseEntity<List<LedgerEntryDto>> getLedgerHistory(
            @PathVariable("accountId") Long accountId,
            @RequestParam(value = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam(value = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        
        List<LedgerEntryDto> history = ledgerService.getLedgerHistory(accountId, startDate, endDate);
        return ResponseEntity.ok(history);
    }

    /**
     * 사용자 계좌에 현금을 입금
     * @param accountId 입금할 사용자 ID
     * @param requestDto 입금 요청 데이터 (금액)
     * @return 입금 후 사용자 계좌의 새로운 잔액 정보 
     */
    @PostMapping("/{accountId}/deposit")
    public ResponseEntity<CashTransactionDto.Response> depositCash(
        @PathVariable("accoundId") Long accountId,
        @Valid @RequestBody CashTransactionDto.Request requestDto) {
     
        CashTransactionDto.Response response = userAccountService.depositCash(accountId, requestDto);
        return ResponseEntity.ok(response);
    }

    /**
     * 사용자 계좌에서 현금을 출금하는 API
     * @param accountId 출금할 사용자 ID
     * @param requestDto 출금 요청 데이터 (금액)
     * @return 출금 후 사용자 계좌의 새로운 잔액 정보
     */
    @PostMapping("/{accountId}/withdraw")
    public ResponseEntity<CashTransactionDto.Response> withdrawCash(
        @PathVariable("accountId") Long accountId,
        @Valid @RequestBody CashTransactionDto.Request requestDto) {


        CashTransactionDto.Response response = userAccountService.withDrawCash(accountId, requestDto);
        return ResponseEntity.ok(response);
    }
    
}

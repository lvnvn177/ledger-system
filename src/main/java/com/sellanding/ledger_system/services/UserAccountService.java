package com.sellanding.ledger_system.services;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sellanding.ledger_system.domain.LedgerEntry;
import com.sellanding.ledger_system.domain.UserAccount;
import com.sellanding.ledger_system.domain.enums.LedgerEntryType;
import com.sellanding.ledger_system.dto.CashTransactionDto;
import com.sellanding.ledger_system.dto.UserAccountDto;
import com.sellanding.ledger_system.repositories.LedgerRepository;
import com.sellanding.ledger_system.repositories.UserAccountRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserAccountService {
    
    private final UserAccountRepository userAccountRepository;
    private final LedgerRepository ledgerRepository;


    /**
    * 새로운 사용자 계정을 생성합니다.
    * @param createDto 사용자 생성 정보
    * @return 생성된 사용자 계정 정보
    */
    @Transactional
    public UserAccountDto.Response createUserAccount(UserAccountDto.Create createDto) {
        // 이미 존재하는 사용자인지 확인
        userAccountRepository.findByUsername(createDto.getUsername()).ifPresent(u -> {
            throw new IllegalStateException("이미 존재하는 사용자 이름입니다.");
        });


        UserAccount newUserAccount = createDto.toEntity();
        UserAccount savedUserAccount = userAccountRepository.save(newUserAccount);
        return UserAccountDto.Response.from(savedUserAccount);
    }

    
    /**
     * 특정 사용자의 계정 정보와 포트폴리오를 조회합니다.
     * @param userId 사용자 ID
     * @return 사용자 계정 및 포트폴리오 정보
     */
    public UserAccountDto.Response getUserAccountDetails(Long userId) {
        UserAccount userAccount = userAccountRepository.findWithPortfoliosByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException("사용자 계정을 찾을 수 없습니다." + userId));
        return UserAccountDto.Response.from(userAccount);
    }
    

    /**
     * 사용자 입금 처리 
     * @param userId 사용자 ID 
     * @param requestDto 입금 요청 정보 
     * @return 입금 처리 결과 
     */
    @Transactional
    public CashTransactionDto.Response depositCash(Long userId, CashTransactionDto.Request requestDto) {
        UserAccount userAccount = userAccountRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("사용자 계정을 찾을 수 없습니다." + userId));

        BigDecimal amount = requestDto.getAmount();
        userAccount.updateBalance(amount);
        userAccountRepository.save(userAccount);

        // 원장 기록 
        createLedgerEntry(userAccount, LedgerEntryType.DEPOSIT, amount);
        
        return new CashTransactionDto.Response(userId, userAccount.getBalance(), "DEPOSIT");
    }

    /**
     * 사용자 출금 처리 
     * @param userId 사용자 ID 
     * @param requestDto 출금 요청 정보 
     * @return 출금 처리 결과 
     */
    @Transactional
    public CashTransactionDto.Response withDrawCash(Long userId, CashTransactionDto.Request requestDto) {
        UserAccount userAccount = userAccountRepository.findById(userId)
            .orElseThrow( () -> new EntityNotFoundException("사용자를 찾을 수 없습니다. " + userId));

        BigDecimal amount = requestDto.getAmount();
        if (userAccount.getBalance().compareTo(amount) < 0) {
            throw new IllegalStateException("잔액이 부족합니다. ");
        }

        userAccount.updateBalance(amount.negate());
        userAccountRepository.save(userAccount);

        // 원장 기록 
        createLedgerEntry(userAccount, LedgerEntryType.WITHDRAWAL, amount.negate());

        return new CashTransactionDto.Response(userId, userAccount.getBalance(), "WITHDRAWAL");
    }

    /**
     * 원장 항목을 생성하고 저장합니다. (입출금 전용)
     * @param userAccount
     * @param type
     * @param amount
     */
    private void createLedgerEntry(UserAccount userAccount, LedgerEntryType type, BigDecimal amount) {
        LedgerEntry entry = LedgerEntry.builder()
                .entryId(UUID.randomUUID().toString())
                .userId(String.valueOf(userAccount.getId()))
                .type(type)
                .amount(amount)
                .timestamp(LocalDateTime.now())
                .build();
        ledgerRepository.save(entry);
    }
}

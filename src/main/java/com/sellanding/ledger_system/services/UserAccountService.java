package com.sellanding.ledger_system.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sellanding.ledger_system.domain.UserAccount;
import com.sellanding.ledger_system.dto.UserAccountDto;
import com.sellanding.ledger_system.repositories.UserAccountRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserAccountService {
    
    private final UserAccountRepository userAccountRepository;


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

}

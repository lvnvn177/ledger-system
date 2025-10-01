package com.sellanding.ledger_system.repositories;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sellanding.ledger_system.domain.LedgerEntry;
import com.sellanding.ledger_system.domain.enums.AssetTicker;
import com.sellanding.ledger_system.domain.enums.LedgerEntryType;




/**
 * 원장 항목(LedgerEntry) 엔티티에 대한 데이터 액세스를 제공하는 리포지토리
 * 원장 시스템의 핵심 데이터인 거래 기록에 대한 CRUD 및 조회 기능 담당
 */

@Repository
public interface LedgerRepository extends JpaRepository<LedgerEntry, Long>{

    /**
     * entryId로 특정 원장 항목을 조회
     * 
     * @param entryId 원장 항목의 고유 식별자
     * @return 찾은 원장 항목(Optional)
     */
    Optional<LedgerEntry> findByEntryId(String entryId);
    
    /**
     * 특정 사용자의 모든 원장 항목을 조회
     * 
     * @param userId 사용자 ID
     * @return 해당 사용자의 모든 원장 항목 목록
     */
    List<LedgerEntry> findByUserId(String userid);

    /**
     * 특정 사용자의 특정 유형 원장 항목을 조회
     * 
     * @param userId 사용자 ID
     * @param type 원장 항목 유형 (입금, 출금, 거래 등)
     * @return 조건에 맞는 원장 항목 목록
     */
    List<LedgerEntry> findByUserIdAndType(String userId, LedgerEntryType type);

    /**
     * 특정 사용자의 특정 기간 내 원장 항목을 조회
     * 
     * @param userId 사용자 ID
     * @param startDate 조회 시작 날짜
     * @param endDate 조회 종료 날짜
     * @return 해당 기간 내 사용자의 원장 항목 목록
     */
    List<LedgerEntry> findByUserIdAndTimestampBetween(
        String userId, LocalDateTime startDate, LocalDateTime endDate);

    /**
     * 특정 사용자의 특정 자산에 대한 거래 내역을 최신순으로 조회
     * 
     * @param userId 사용자 ID
     * @param assetSymbol 자산 티커 (예: "SPY", "QQQ")
     * @return 해당 자산에 대한 거래 내역 목록
     */
    @Query("SELECT l FROM LedgerEntry l WHERE l.userId = :userId " +
           "And l.assetTicker = :assetTicker ORDER By l.timestamp DESC")
    List<LedgerEntry> findUserAssetTransactions(
        @Param("userId") String userId,
        @Param("assetTicker") AssetTicker assetTicker
    );

    /**
     * 특정 사용자의 현금 잔액을 계산
     * 입금과 출금 타입의 항목만 고려하여 합산
     * 
     * @param userId 사용자 ID
     * @return 현재 현금 잔액 (Optional)
     */
    @Query("SELECT SUM(l.amount) FROM LedgerEntry l WHERE l.userId = :userId " +
        "AND l.type IN ('DEPOSIT', 'WITHDRAWAL')")
    Optional<Double> calculateUserCashBalance(@Param("userId") String userId);
}

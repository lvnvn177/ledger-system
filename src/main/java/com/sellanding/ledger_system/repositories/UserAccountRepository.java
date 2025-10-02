package com.sellanding.ledger_system.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sellanding.ledger_system.domain.UserAccount;
import com.sellanding.ledger_system.domain.enums.AssetTicker;

@Repository
/**
 * 사용자 계정(UserAccount) 엔티티에 대한 데이터 액세스를 제공하는 리포지토리
 * 사용자 정보, 포트폴리오 조회 등 사용자 관련 데이터 처리 담당
 */
public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {
    
    /**
     * 사용자 이름으로 특정 사용자 계정을 조회
     *
     * @param username 사용자 이름
     * @return 찾은 사용자 계정(Optional)
     */
    Optional<UserAccount> findByUsername(String username);

    /**
     * 특정 사용자의 포트폴리오 정보를 함께 조회
     * Fetch Join을 사용하여 N+1 문제 방지
     *
     * @param userId 사용자 ID
     * @return 포트폴리오를 포함한 사용자 계정(Optional)
     */
    @Query("SELECT ua FROM UserAccount ua JOIN FETCH ua.portfolio " +
           "WHERE ua.id = :userId")
    Optional<UserAccount> findWithPortfoliosByUserId(@Param("userId") Long userId);

    /**
     * 특정 자산을 보유한 모든 사용자를 조회
     *
     * @param assetTicker 조회할 자산의 티커
     * @return 해당 자산을 보유한 모든 사용자 목록
     */
    @Query("SELECT DISTINCT ua FROM UserAccount ua JOIN ua.portfolio p " +
          "WHERE p.assetTicker = :assetTicker")
    List<UserAccount> findUsersWithSpecificAsset(@Param("assetTicker") AssetTicker assetTicker);
    
}

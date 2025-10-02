package com.sellanding.ledger_system.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sellanding.ledger_system.domain.Order;
import com.sellanding.ledger_system.domain.enums.AssetTicker;
import com.sellanding.ledger_system.domain.enums.OrderStatus;

@Repository
/**
 * 주문(Order) 엔티티에 대한 데이터 액세스를 제공하는 리포지토리
 * 주문 생성, 조회, 상태 변경 등 주문 관련 데이터 처리 담당
 */
public interface OrderRepository extends JpaRepository<Order, Long>{
    
    /**
     * 특정 사용자의 모든 주문을 조회
     *
     * @param userId 사용자 ID
     * @return 해당 사용자의 모든 주문 목록
     */
    List<Order> findByUserAccountId(Long userId);

    /**
     * 특정 사용자의 특정 상태 주문을 조회
     *
     * @param userId 사용자 ID
     * @param status 조회할 주문 상태
     * @return 조건에 맞는 주문 목록
     */
    List<Order> findByUserAccountIdAndStatus(Long userId, OrderStatus status);

    /**
     * 특정 상태의 모든 주문을 조회
     *
     * @param status 조회할 주문 상태
     * @return 해당 상태의 모든 주문 목록
     */
    List<Order> findByStatus(OrderStatus status);

    /**
     * 특정 사용자의 특정 자산에 대한 주문을 조회
     *
     * @param userId 사용자 ID
     * @param assetTicker 조회할 자산의 티커
     * @return 조건에 맞는 주문 목록
     */
    List<Order> findByUserAccountIdAndAssetTicker(Long userId, AssetTicker assetTicker);

    /**
     * 특정 사용자의 특정 주문을 조회
     *
     * @param userId 사용자 ID
     * @param orderId 주문 ID
     * @return 찾은 주문(Optional)
     */
    Optional<Order> findByUserAccountIdAndId(Long userId, Long orderId);
}

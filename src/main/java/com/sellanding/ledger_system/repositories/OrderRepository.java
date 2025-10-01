package com.sellanding.ledger_system.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sellanding.ledger_system.domain.Order;
import com.sellanding.ledger_system.domain.enums.AssetTicker;
import com.sellanding.ledger_system.domain.enums.OrderStatus;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long>{
    
    // 특정 사용자 ID로 모든 주문 조회
    List<Order> findByUserId(Long userId);

    // 특정 사용자 ID와 상태로 주문 조회
    List<Order> findByUserIdAndStatus(Long userId, OrderStatus status);

    // 특정 상태의 모든 주문 조회
    List<Order> findByStatus(OrderStatus status);

    // 특정 사용자의 특정 자산(assetTicker)에 대한 주문 조회
    List<Order> findByUserIdAndAssetTicker(Long userId, AssetTicker assetTicker);

    // 사용자 ID와 주문 ID로 특정 주문 조회
    Optional<Order> findByUserIdAndId(Long userId, Long orderId);
}

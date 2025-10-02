package com.sellanding.ledger_system.services;

import com.sellanding.ledger_system.domain.LedgerEntry;
import com.sellanding.ledger_system.domain.Order;
import com.sellanding.ledger_system.domain.Position;
import com.sellanding.ledger_system.domain.UserAccount;
import com.sellanding.ledger_system.domain.enums.LedgerEntryType;
import com.sellanding.ledger_system.domain.enums.OrderSide;
import com.sellanding.ledger_system.domain.enums.OrderStatus;
import com.sellanding.ledger_system.dto.LedgerEntryDto;
import com.sellanding.ledger_system.dto.TransactionRequestDto;
import com.sellanding.ledger_system.dto.TransactionResponseDto;
import com.sellanding.ledger_system.repositories.LedgerRepository;
import com.sellanding.ledger_system.repositories.OrderRepository;
import com.sellanding.ledger_system.repositories.UserAccountRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LedgerService {

    private final LedgerRepository ledgerRepository;
    private final UserAccountRepository userAccountRepository;
    private final OrderRepository orderRepository;
    // private final EventPublisher eventPublisher; // 이벤트 발행 기능은 주석 처리

    /**
     * 사용자의 거래 요청을 처리합니다. (매수/매도)
     * @param requestDto 거래 요청 정보
     * @return 거래 처리 결과
     */
    @Transactional
    public TransactionResponseDto processTransaction(TransactionRequestDto requestDto) {
        UserAccount user = userAccountRepository.findById(requestDto.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("사용자를 찾을 수 없습니다: " + requestDto.getUserId()));

        Order order = createOrderFromRequest(requestDto, user);

        if (requestDto.getSide() == OrderSide.BUY) {
            processBuyOrder(user, order);
        } else {
            processSellOrder(user, order);
        }

        order.updateStatus(OrderStatus.COMPLETED);
        orderRepository.save(order);

        // 이벤트 발행 로직 (필요시 주석 해제)
        // eventPublisher.publishOrderCompletedEvent(order);

        return TransactionResponseDto.from(order);
    }

    /**
     * 매수 주문을 처리합니다.
     */
    private void processBuyOrder(UserAccount user, Order order) {
        BigDecimal totalCost = order.getPrice().multiply(BigDecimal.valueOf(order.getQuantity()));
        if (user.getBalance().compareTo(totalCost) < 0) {
            throw new IllegalStateException("잔액이 부족합니다.");
        }

        user.updateBalance(totalCost.negate());
        createLedgerEntry(user, order, LedgerEntryType.TRADE_BUY, totalCost.negate());
        
        updateUserPosition(user, order, OrderSide.BUY);
    }

    /**
     * 매도 주문을 처리합니다.
     */
    private void processSellOrder(UserAccount user, Order order) {
        Position position = user.getPortfolios().stream()
                .filter(p -> p.getAssetTicker().equals(order.getAssetTicker()))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("보유하지 않은 자산입니다."));

        if (position.getQuantity() < order.getQuantity()) {
            throw new IllegalStateException("보유 수량보다 많이 매도할 수 없습니다.");
        }

        BigDecimal totalGain = order.getPrice().multiply(BigDecimal.valueOf(order.getQuantity()));
        user.updateBalance(totalGain);
        createLedgerEntry(user, order, LedgerEntryType.TRADE_SELL, totalGain);

        updateUserPosition(user, order, OrderSide.SELL);
    }

    /**
     * 사용자의 자산 포지션을 업데이트합니다.
     */
    private void updateUserPosition(UserAccount user, Order order, OrderSide side) {
        Optional<Position> existingPosition = user.getPortfolios().stream()
                .filter(p -> p.getAssetTicker().equals(order.getAssetTicker()))
                .findFirst();

        if (side == OrderSide.BUY) {
            if (existingPosition.isPresent()) {
                existingPosition.get().addQuantity(order.getQuantity(), order.getPrice());
            } else {
                Position newPosition = Position.builder()
                    .userAccount(user)
                    .assetTicker(order.getAssetTicker())
                    .quantity(order.getQuantity())
                    .avgBuyPrice(order.getPrice())
                    .build();
                user.addPortfolio(newPosition);
            }
        } else { // SELL
            Position position = existingPosition.orElseThrow(() -> new IllegalStateException("매도할 자산이 없습니다."));
            position.subtractQuantity(order.getQuantity());
        }
    }
    
    /**
     * 거래 요청으로부터 주문 객체를 생성합니다.
     */
    private Order createOrderFromRequest(TransactionRequestDto requestDto, UserAccount user) {
        Order order = Order.builder()
                .userAccount(user)
                .assetTicker(requestDto.getAssetTicker())
                .side(requestDto.getSide())
                .quantity(requestDto.getQuantity())
                .price(requestDto.getPrice())
                .status(OrderStatus.PENDING)
                .timestamp(LocalDateTime.now())
                .build();
        return orderRepository.save(order);
    }

    /**
     * 원장 항목을 생성하고 저장합니다.
     */
    private void createLedgerEntry(UserAccount user, Order order, LedgerEntryType type, BigDecimal amount) {
        LedgerEntry entry = LedgerEntry.builder()
                .entryId(UUID.randomUUID().toString())
                .userId(user.getId().toString())
                .orderId(order.getId().toString())
                .type(type)
                .assetTicker(order.getAssetTicker())
                .amount(amount)
                .timestamp(LocalDateTime.now())
                .build();
        ledgerRepository.save(entry);
    }   
    
    /**
     * 사용자의 원장 내역을 조회 (선택적으로 기간 필터링 가능)
     * @param userId 사용자 ID
     * @param startDate 조회 시작 날짜 (null 가능)
     * @param endDate 조회 종료 날짜 (null 가능)
     * @return 해당 사용자의 원장 항목 목록
     */
    public List<LedgerEntryDto> getLedgerHistory (Long userId, LocalDateTime startDate, LocalDateTime endDate) {

        String userIdStr = String.valueOf(userId);
        
        List<LedgerEntry> ledgerEntries;

        // 기간 필터링 
        if (startDate != null && endDate != null) {
            ledgerEntries = ledgerRepository.findByUserIdAndTimestampBetween(userIdStr, startDate, endDate);
        } else {
            ledgerEntries = ledgerRepository.findByUserId(userIdStr);
        }

        return ledgerEntries.stream()
                .map(LedgerEntryDto::from)
                .collect(Collectors.toList());
    }
}

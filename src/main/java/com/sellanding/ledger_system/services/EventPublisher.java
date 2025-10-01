package com.sellanding.ledger_system.services;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sellanding.ledger_system.domain.Event;
import com.sellanding.ledger_system.domain.enums.AssetTicker;
import com.sellanding.ledger_system.domain.enums.EventType;
import com.sellanding.ledger_system.domain.enums.LedgerEntryType;
import com.sellanding.ledger_system.repositories.EventRepository;

import jakarta.transaction.Transactional;

@Service
public class EventPublisher {
    
    private final EventRepository eventRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    
    public EventPublisher(EventRepository eventRepository,
                    KafkaTemplate<String, String> kafkaTemplate,  
                    ObjectMapper objectMapper) {
      this.eventRepository = eventRepository;
      this.kafkaTemplate = kafkaTemplate;
      this.objectMapper = objectMapper;                        
    }

    @Transactional
    public Event publishBalanceChanged(Long userId, BigDecimal amount, 
                                    LedgerEntryType type, String ledgerEntryId) {
        
        Map<String, Object> eventData = new HashMap<>();
        eventData.put("userId", userId);
        eventData.put("amount", amount);
        eventData.put("type", type);
        eventData.put("ledgerEntryId", ledgerEntryId);

        try {
            String eventDataJson = objectMapper.writeValueAsString(eventData);

            Event event = Event.builder()
                .eventType(type == LedgerEntryType.DEPOSIT ?
                        EventType.BALANCE_DEPOSITED : EventType.BALANCE_WITHDRAWN)
                .aggregateId(userId.toString())
                .eventData(eventDataJson)
                .userId(userId.toString())
                .build();
            
            Event savedEvent = eventRepository.save(event);

            // Kafka에 이벤트 발행 
            kafkaTemplate.send("ledger-events", savedEvent.getEventId(), eventDataJson);

            return savedEvent;
        } catch (Exception e) {
            throw new RuntimeException("Failed to publish balance changed event", e);
        }
    }

        @Transactional
    public Event publishTradeExecuted(Long userId, AssetTicker assetTicker, Long quantity, 
                                    BigDecimal price, LedgerEntryType tradeType, String ledgerEntryId) {
        
        Map<String, Object> eventData = new HashMap<>();
        eventData.put("userId", userId);
        eventData.put("assetTicker", assetTicker);
        eventData.put("quantity", quantity);
        eventData.put("price", price);
        eventData.put("tradeType", tradeType);
        eventData.put("ledgerEntryId", ledgerEntryId);
        
        try {
            String eventDataJson = objectMapper.writeValueAsString(eventData);
            
            EventType eventType = tradeType == LedgerEntryType.TRADE_BUY ? 
                                EventType.ASSET_ACQUIRED : EventType.ASSET_SOLD;
            
            Event event = Event.builder()
                .eventType(eventType)
                .aggregateId(userId.toString())
                .eventData(eventDataJson)
                .userId(userId.toString())
                .build();
            
            Event savedEvent = eventRepository.save(event);
            
            // Kafka에 이벤트 발행
            kafkaTemplate.send("trade-events", savedEvent.getEventId(), eventDataJson);
            
            return savedEvent;
        } catch (Exception e) {
            throw new RuntimeException("Failed to publish trade executed event", e);
        }
    }
    
    @Transactional
    public Event publishOrderEvent(Long userId, String orderId, EventType eventType, 
                                 Map<String, Object> additionalData) {
        
        Map<String, Object> eventData = new HashMap<>(additionalData);
        eventData.put("userId", userId);
        eventData.put("orderId", orderId);
        
        try {
            String eventDataJson = objectMapper.writeValueAsString(eventData);
            
            Event event = Event.builder()
                .eventType(eventType)
                .aggregateId(orderId)
                .eventData(eventDataJson)
                .userId(userId.toString())
                .build();
            
            Event savedEvent = eventRepository.save(event);
            
            // Kafka에 이벤트 발행
            kafkaTemplate.send("order-events", savedEvent.getEventId(), eventDataJson);
            
            return savedEvent;
        } catch (Exception e) {
            throw new RuntimeException("Failed to publish order event", e);
        }
    }
    
    @Transactional
    public Event publishSystemEvent(EventType eventType, String aggregateId, 
                                  Map<String, Object> eventData, String userId) {
        try {
            String eventDataJson = objectMapper.writeValueAsString(eventData);
            
            Event event = Event.builder()
                .eventType(eventType)
                .aggregateId(aggregateId)
                .eventData(eventDataJson)
                .userId(userId)
                .build();
            
            Event savedEvent = eventRepository.save(event);
            
            // 시스템 이벤트는 별도 토픽으로 발행
            kafkaTemplate.send("system-events", savedEvent.getEventId(), eventDataJson);
            
            return savedEvent;
        } catch (Exception e) {
            throw new RuntimeException("Failed to publish system event", e);
        }
    }

}


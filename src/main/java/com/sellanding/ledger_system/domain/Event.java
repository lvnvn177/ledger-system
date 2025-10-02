package com.sellanding.ledger_system.domain;

import java.time.LocalDateTime;
import java.util.UUID;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import com.sellanding.ledger_system.domain.enums.EventType;
/**
 * 원장 시스템 내 모든 이벤트를 기록하는 도메인 클래스
 * 이벤트 소싱 패턴 구현을 위한 불변 객체로 설계 
 */

@Entity
@Table(name = "events")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String eventId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EventType eventType;

    @Column(nullable = false)
    private String aggregateId;

    @Column(nullable = false)
    private Integer version;

    @Column(nullable = false)
    private LocalDateTime timeStamp;

    @Column(nullable = false, length = 5000)
    private String eventData;

    @Column(length = 1000)
    private String metadata;

    @Column(nullable = false)
    private String userId;

    // 생성자 - 새 이벤트 생성 시 사용
    protected Event() {
        // JPA를 위한 기본 생성자
    }

    // 이벤트 생성을 위한 빌더 패턴 구현
    public static EventBuilder builder() {
        return new EventBuilder();
    }

    // 게터 메서드들
    public Long getId() {
        return id;
    }

    public String getEventId() {
        return eventId;
    }

    public EventType getEventType() {
        return eventType;
    }

    public String getAggregateId() {
        return aggregateId;
    }

    public Integer getVersion() {
        return version;
    }

    public LocalDateTime getTimestamp() {
        return timeStamp;
    }

    public String getEventData() {
        return eventData;
    }

    public String getMetadata() {
        return metadata;
    }

    public String getUserId() {
        return userId;
    }

    // 이벤트 빌더 클래스
    public static class EventBuilder {
        private final Event event;

        private EventBuilder() {
            event = new Event();
            event.eventId = UUID.randomUUID().toString();
            event.timeStamp = LocalDateTime.now();
            event.version = 1;
        }

        public EventBuilder eventType(EventType eventType) {
            event.eventType = eventType;
            return this;
        }

        public EventBuilder aggregateId(String aggregateId) {
            event.aggregateId = aggregateId;
            return this;
        }

        public EventBuilder version(Integer version) {
            event.version = version;
            return this;
        }

        public EventBuilder eventData(String eventData) {
            event.eventData = eventData;
            return this;
        }

        public EventBuilder metadata(String metadata) {
            event.metadata = metadata;
            return this;
        }

        public EventBuilder userId(String userId) {
            event.userId = userId;
            return this;
        }

        public Event build() {
            if (event.eventType == null) {
                throw new IllegalStateException("Event type cannot be null");
            }
            if (event.aggregateId == null) {
                throw new IllegalStateException("Aggregate ID cannot be null");
            }
            if (event.eventData == null) {
                throw new IllegalStateException("Event data cannot be null");
            }
            if (event.userId == null) {
                throw new IllegalStateException("User ID cannot be null");
            }
            return event;
        }
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", eventId='" + eventId + '\'' +
                ", eventType=" + eventType +
                ", aggregateId='" + aggregateId + '\'' +
                ", version=" + version +
                ", timeStamp=" + timeStamp +
                '}';
    }
}

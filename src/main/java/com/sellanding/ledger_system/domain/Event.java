package com.sellanding.ledger_system.domain;

import javax.swing.event.DocumentEvent.EventType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

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

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EventType eventType;

}

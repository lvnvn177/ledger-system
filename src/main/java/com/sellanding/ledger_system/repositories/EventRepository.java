package com.sellanding.ledger_system.repositories;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sellanding.ledger_system.domain.Event;
import com.sellanding.ledger_system.domain.enums.EventType;

/**
 * 이벤트 엔티티에 대한 데이터 액세스를 제공하는 리포지토리
 * 이벤트 소싱 패턴 구현을 위한 불변 이벤트 기록 관리
 */
@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    

    /**
     * eventId로 특정 이벤트를 조회
     * 
     * @param eventId 이벤트의 고유 식별자
     * @return 찾은 이벤트(Optional)
     */
    Optional<Event> findByEventId(String eventId);

    /**
     * 특정 애그리게이트에 속한 모든 이벤트 조회
     * 
     * @param aggregateId 애그리게이트 식별자
     * @return 해당 애그리게이트의 모든 이벤트 목록
     */
    List<Event> findByAggregateId(String aggregateId);

    /**
     * 특정 유형의 모든 이벤트 조회
     * 
     * @param eventType 이벤트 유형
     * @return 해당 유형의 모든 이벤트 목록
     */
    List<Event> findByEventType(EventType eventType);

    /**
     * 특정 애그리게이트의 특정 유형 이벤트 조회
     * 
     * @param aggregateId 애그리게이트 식별자
     * @param eventType 이벤트 유형
     * @return 조건에 맞는 이벤트 목록
     */
    List<Event> findByAggregateIdAndEventType(String aggregateId, EventType eventType);

    /**
     * 특정 기간 내 발생한 이벤트 조회
     * 
     * @param startDate 조회 시작 날짜
     * @param endDate 조회 종료 날짜
     * @return 해당 기간 내 발생한 이벤트 목록
     */
    List<Event> findByTimeStampBetween(LocalDateTime startDate, LocalDateTime endDate);


    /**
     * 특정 애그리게이트의 이벤트 스트림을 버전 순으로 조회
     * 이벤트 소싱 패턴에서 애그리게이트 재구성에 사용
     * 
     * @param aggregateId 애그리게이트 식별자
     * @return 버전 순으로 정렬된 이벤트 목록
     */
    @Query("SELECT e FROM Event e WHERE e.aggregateId = :aggregateId " +
          "ORDER BY e.version ASC")
    List<Event> findEventStreamByAggregateId(@Param("aggregateId") String aggregateId);

     /**
     * 특정 애그리게이트의 최신 버전 번호 조회
     * 새 이벤트 추가 시 버전 충돌 방지에 활용
     * 
     * @param aggregateId 애그리게이트 식별자
     * @return 최신 버전 번호(Optional)
     */
    @Query("SELECT MAX(e.version) FROM Event e WHERE e.aggregateId = :aggregateId")
    Optional<Integer> findLatestVersionByAggreagateId(@Param("aggregateId") String aggregateId);

    /**
     * 특정 사용자가 발생시킨 모든 이벤트 조회
     * 
     * @param userId 사용자 ID
     * @return 해당 사용자의 모든 이벤트 목록
     */
    List<Event> findByUserId(String userId);

    /**
     * 특정 기간 내 특정 유형의 이벤트만 조회
     * 
     * @param eventType 이벤트 유형
     * @param startDate 조회 시작 날짜
     * @param endDate 조회 종료 날짜
     * @return 조건에 맞는 이벤트 목록
     */
    List<Event> findByEventTypeAndTimestampBetween(
        EventType eventType, LocalDateTime startDate, LocalDateTime endDate);
    
    /**
     * 시스템 이벤트만 조회 (감사 및 모니터링용)
     * 
     * @return 모든 시스템 이벤트 목록
     */
    @Query("SELECT e FROM Event e WHERE e.eventType IN " + 
        "('SYSTEM_ERROR', 'AUDIT_LOG_CREATED', 'DATA_SNAPSHOT_CREATED')")
    List<Event> findAllSystemEvents();
}
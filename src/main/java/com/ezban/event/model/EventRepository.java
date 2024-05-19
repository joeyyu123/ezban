package com.ezban.event.model;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Timestamp;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Integer> {
    List<Event> findByHostHostNo(Integer HostNo);

    List<Event> findByEventCity(String eventCity);

    List<Event> findByEventStatus(EventStatus eventStatus);
    List<Event> findByEventStatus(EventStatus status, Pageable pageable);

    List<Event> findByEventCategoryEventCategoryNoAndEventStatus(Integer eventCategoryNo, EventStatus eventStatus);

    /**
     * 取得所有草稿狀態且上架時間小於等於現在時間的活動
     * @param eventStatus 活動狀態
     * @param eventAddTime 活動上架時間
     * @return List<Event>
     */
    List<Event> findByEventStatusAndEventAddTimeBefore(EventStatus eventStatus, Timestamp eventAddTime);

    /**
     * 取得所有活動狀態為上架且結束時間小於等於現在時間的活動
     * @param eventStatus 活動狀態
     * @param eventEndTime 活動結束時間
     * @return List<Event>
     */
    List<Event> findByEventStatusAndEventEndTimeBefore(EventStatus eventStatus, Timestamp eventEndTime);

    List<Event> findByHostHostNoAndEventStatus(Integer host_hostNo, EventStatus eventStatus);
}

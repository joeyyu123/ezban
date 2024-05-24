package com.ezban.event.model;

import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.sql.Timestamp;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Integer>, JpaSpecificationExecutor<Event> {
    List<Event> findByHostHostNoOrderByEventNoDesc(Integer HostNo, Sort sort);

    List<Event> findByEventCityAndEventStatus(String eventCity,EventStatus eventStatus, Pageable pageable);

    List<Event> findByEventStatus(EventStatus eventStatus);
    List<Event> findByEventStatus(EventStatus status, Pageable pageable);

    List<Event> findByEventCategoryEventCategoryNoAndEventStatus(Integer eventCategoryNo, EventStatus eventStatus, Pageable pageable);

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

    List<Event> findByHostHostNoAndEventStatusOrderByEventNoDesc(Integer host_hostNo, EventStatus eventStatus);

    @Query("SELECT DISTINCT e.eventCity FROM Event e")
    List<String> findDistinctEventCity();

    List<Event> findByEventCityAndEventCategoryEventCategoryNoAndEventStatus(String city, Integer categoryNo, EventStatus eventStatus, Pageable pageable);

    @Query("SELECT e FROM Event e WHERE e.eventStatus = :status AND " +
            "(e.eventCity IN :cities OR :cities IS NULL) AND " +
            "(e.eventCategory.eventCategoryNo IN :categoryNos OR :categoryNos IS NULL)")
    List<Event> findByEventCityAndEventCategory(@Param("status") EventStatus status,
                                                @Param("cities") List<String> cities,
                                                @Param("categoryNos") List<Integer> categoryNos,
                                                Pageable pageable);

    //host聊天室用的
    @Query("SELECT e.eventNo FROM Event e WHERE e.host.hostNo = :hostNo")
    List<Integer> findEventNoByHostNo(@Param("hostNo") Integer hostNo);


    @Query("SELECT e FROM Event e JOIN FETCH e.eventCategory JOIN FETCH e.host")
    List<Event> findTop6ByOrderByVisitCountDesc(Pageable pageable);
}

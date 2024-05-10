package com.ezban.event.model;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Integer> {
    List<Event> findByHostHostNo(Integer HostNo);

    List<Event> findByEventCity(String eventCity);

    List<Event> findByEventStatus(EventStatus eventStatus);

    List<Event> findByEventCategoryEventCategoryNoAndEventStatus(Integer eventCategoryNo, EventStatus eventStatus);

}

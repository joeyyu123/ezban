package com.ezban.eventcategory.model;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EventCategoryRepository extends JpaRepository<EventCategory,Integer> {
    EventCategoryRepository findByEventCategoryName(String eventCategoryName);
}

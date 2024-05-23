package com.ezban.eventcomment.model;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventCommentRepository extends JpaRepository<EventComment, Integer> {
    List<EventComment> findByEvent_EventNo(Integer eventNo);
    long countByEvent_EventNo(Integer eventNo);
}

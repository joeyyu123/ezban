package com.ezban.eventcommentreport.model;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventCommentReportRepository extends JpaRepository<EventCommentReport, Integer> {
    List<EventCommentReport> findByEventComment_EventCommentNo(Integer eventCommentNo);
}

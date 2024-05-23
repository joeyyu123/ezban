package com.ezban.eventcommentreport.model;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ezban.eventcomment.model.EventComment;
import com.ezban.eventcomment.model.EventCommentRepository;

@Service
public class EventCommentReportService {

    private final EventCommentReportRepository reportRepository;
    private final EventCommentRepository commentRepository;

    @Autowired
    public EventCommentReportService(EventCommentReportRepository reportRepository, EventCommentRepository commentRepository) {
        this.reportRepository = reportRepository;
        this.commentRepository = commentRepository;
    }

    public EventCommentReport save(EventCommentReport report) {
        return reportRepository.save(report);
    }

    @Transactional(readOnly = true)
    public List<EventCommentReport> findAll() {
        return reportRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<EventCommentReport> findById(Integer reportId) {
        return reportRepository.findById(reportId);
    }

    @Transactional
    public void saveComment(EventComment comment) {
        commentRepository.save(comment);
    }

    @Transactional
    public void updateAllReportsWithCommentNo(Integer commentNo, Byte reportStatus) {
        List<EventCommentReport> reports = reportRepository.findByEventComment_EventCommentNo(commentNo);
        reports.forEach(report -> report.setReportStatus(reportStatus));
        reportRepository.saveAll(reports);
    }
}

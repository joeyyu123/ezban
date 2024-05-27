package com.ezban.eventcomment.model;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ezban.event.model.Event;
import com.ezban.event.model.EventRepository;

@Service
public class EventCommentService {

    private final EventCommentRepository commentRepository;
    private final EventRepository eventRepository;
    
    @Autowired
    public EventCommentService(EventCommentRepository commentRepository, EventRepository eventRepository) {
        this.commentRepository = commentRepository;
        this.eventRepository = eventRepository;
    }

    public EventComment save(EventComment comment) {
        return commentRepository.save(comment);
    }

    @Transactional(readOnly = true)
    public List<EventCommentDTO> findAllComments() {
        List<EventComment> comments = commentRepository.findAll();
        comments.forEach(comment -> Hibernate.initialize(comment.getMember())); // 初始化延遲加載的Member
        return comments.stream()
                       .map(comment -> new EventCommentDTO(
                               comment.getEventCommentNo(),
                               comment.getMember().getMemberNo(),
                               comment.getEvent().getEventNo(),
                               comment.getEventCommentContent(),
                               comment.getEventCommentRate(),
                               comment.getEventCommentStatus())) // 包含status字段
                       .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<EventCommentDTO> findCommentsByEventNo(Integer eventNo) {
        List<EventComment> comments = commentRepository.findByEvent_EventNo(eventNo);
        comments.forEach(comment -> Hibernate.initialize(comment.getMember())); // 初始化延遲加載的Member
        return comments.stream()
                       .map(comment -> new EventCommentDTO(
                               comment.getEventCommentNo(),
                               comment.getMember().getMemberNo(),
                               comment.getEvent().getEventNo(),
                               comment.getEventCommentContent(),
                               comment.getEventCommentRate(),
                               comment.getEventCommentStatus())) // 包含status字段
                       .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public double getAverageRating(Integer eventNo) {
        List<EventComment> comments = commentRepository.findByEvent_EventNo(eventNo);
        return comments.stream()
                       .mapToInt(EventComment::getEventCommentRate)
                       .average()
                       .orElse(0.0);
    }

    @Transactional(readOnly = true)
    public long getRatingCount(Integer eventNo) {
        return commentRepository.countByEvent_EventNo(eventNo);
    }

    @Transactional(readOnly = true)
    public EventCommentDTO.CommentStatsDTO getCommentStats(Integer eventNo) {
        double averageRating = getAverageRating(eventNo);
        long ratingCount = getRatingCount(eventNo);
        return new EventCommentDTO.CommentStatsDTO(averageRating, ratingCount);
    }
    @Transactional(readOnly = true)
    public Event findEventById(Integer eventId) {
        Optional<Event> event = eventRepository.findById(eventId);
        return event.orElse(null);
    }
}

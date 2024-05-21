package com.ezban.eventcommentcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ezban.eventcomment.model.EventComment;
import com.ezban.eventcomment.model.EventCommentDTO;
import com.ezban.eventcomment.model.EventCommentService;

@RestController
@RequestMapping("/api/event/comment")
public class EventCommentController {

    private final EventCommentService commentService;

    @Autowired
    public EventCommentController(EventCommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    public ResponseEntity<EventComment> addComment(@RequestBody EventComment comment) {
        try {
            EventComment savedComment = commentService.save(comment);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedComment);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/{eventNo}")
    public ResponseEntity<List<EventCommentDTO>> getCommentsByEventNo(@PathVariable Integer eventNo) {
        try {
            List<EventCommentDTO> comments = commentService.findCommentsByEventNo(eventNo);
            return ResponseEntity.ok(comments);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/stats/{eventNo}")
    public ResponseEntity<EventCommentDTO.CommentStatsDTO> getCommentStats(@PathVariable Integer eventNo) {
        try {
            EventCommentDTO.CommentStatsDTO stats = commentService.getCommentStats(eventNo);
            return ResponseEntity.ok(stats);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}

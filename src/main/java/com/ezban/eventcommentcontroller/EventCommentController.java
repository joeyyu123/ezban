package com.ezban.eventcommentcontroller;

import java.security.Principal;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ezban.event.model.Event;
import com.ezban.eventcategory.model.EventCategory;
import com.ezban.eventcategory.model.EventCategoryService;
import com.ezban.eventcomment.model.EventComment;
import com.ezban.eventcomment.model.EventCommentDTO;
import com.ezban.eventcomment.model.EventCommentService;
import com.ezban.member.model.Member;

@RestController
@RequestMapping("/api/event/comment")
public class EventCommentController {

    private final EventCommentService commentService;
    private final EventCategoryService categoryService;

    @Autowired
    public EventCommentController(EventCommentService commentService, EventCategoryService categoryService) {
        this.commentService = commentService;
        this.categoryService = categoryService;
    }

    @PostMapping(consumes = "application/json;charset=UTF-8")
    public ResponseEntity<?> addComment(@RequestBody EventCommentDTO commentDTO, Principal principal) {
        System.out.println("Received POST request to add comment");
        try {
            Integer memberNo = Integer.parseInt(principal.getName());
            Member member = new Member(memberNo);
            Event event = new Event(commentDTO.getEventNo());

            EventComment comment = new EventComment(commentDTO, member, event);
            EventComment savedComment = commentService.save(comment);

            return ResponseEntity.status(HttpStatus.CREATED).body(savedComment);
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body("Invalid member number format: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error saving comment: " + e.getMessage());
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

    @GetMapping("/category/{categoryNo}")
    public ResponseEntity<?> getEventAndMemberInfoByCategoryNo(@PathVariable Integer categoryNo, Principal principal) {
        try {
            EventCategory category = categoryService.findById(categoryNo);
            if (category == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Event category not found");
            }

            Set<Event> events = category.getEvents();
            if (events.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No events found for the category");
            }

            Iterator<Event> iterator = events.iterator();
            if (iterator.hasNext()) {
                Event event = iterator.next();
                Integer eventNo = event.getEventNo();
                Integer memberNo = Integer.parseInt(principal.getName());

                return ResponseEntity.ok().body(new EventCommentDTO.EventAndMemberDTO(eventNo, memberNo));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No events found for the category");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error retrieving event and member info: " + e.getMessage());
        }
    }
}

package com.ezban.eventcomment;

import com.ezban.eventcomment.model.EventComment;
import com.ezban.eventcomment.model.EventCommentReviewService;
import com.ezban.member.model.Member;
import com.ezban.member.model.MemberService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/eventCommentsReview")
public class EventCommentController {

	@Autowired
    private EventCommentReviewService eventCommentService;

    @Autowired
    private MemberService memService;

    @GetMapping("/member/{memberNo}")
    @ResponseBody
    public ResponseEntity<List<EventComment>> getCommentsByMember(@PathVariable("memberNo") Integer memberNo) {
        Member member = memService.getMemberById(memberNo);
        if (member == null) {
            return ResponseEntity.notFound().build();
        }
        List<EventComment> comments = eventCommentService.getCommentsByMember(member);
        return ResponseEntity.ok(comments);
    }

}

package com.ezban.eventcomment;

import com.ezban.eventcomment.model.EventComment;
import com.ezban.eventcomment.model.EventCommentReviewService;
import com.ezban.member.model.Member;
import com.ezban.member.model.MemberService;
import com.ezban.productcomment.model.ProductCommentReviewService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/eventCommentsReview")
public class EventCommentReviewController {

	@Autowired
    private EventCommentReviewService eventCommentService;
	
	@Autowired
    private ProductCommentReviewService productCommentService;

    @Autowired
    private MemberService memService;
   

    @GetMapping("/member/{memberNo}")
    @ResponseBody
    public ResponseEntity<List<EventComment>> getCommentsByMember(@PathVariable("memberNo") Integer memberNo,Principal principal) {
        Member member = memService.getMemberById(Integer.parseInt(principal.getName()));
        if (member == null) {
            return ResponseEntity.notFound().build();
        }
        List<EventComment> comments = eventCommentService.getCommentsByMember(member);
        return ResponseEntity.ok(comments);
    }
    
    @GetMapping("/commentReviewPage")
    public String getCommentReviewPage(Model model, Principal principal) {
        Optional<Member> currentMember = memService.findMemberByMemberNo(principal.getName());
        if (currentMember.isPresent()) {
            Member member = currentMember.get();
            model.addAttribute("eventComments", eventCommentService.getCommentsByMember(member));
            model.addAttribute("productComments", productCommentService.getCommentsByMember(member));
            return "frontstage/commentreview/commentReview";
        } else {
            model.addAttribute("errorMessage", "未找到該成員");
            return "errorPage";
        }
    }



}

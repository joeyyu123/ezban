package com.ezban.productcomment;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ezban.eventcomment.model.EventCommentReviewService;
import com.ezban.member.model.Member;
import com.ezban.member.model.MemberService;
import com.ezban.productcomment.model.ProductComment;
import com.ezban.productcomment.model.ProductCommentReviewService;

@Controller
@RequestMapping("/productCommentsReview")
public class ProductCommentReviewController {

    @Autowired
    private ProductCommentReviewService productCommentService;
    
    @Autowired
    private EventCommentReviewService eventCommentService;

    @Autowired
    private MemberService memService;

    @GetMapping("/member/{memberNo}")
    @ResponseBody
    public ResponseEntity<List<ProductComment>> getCommentsByMember(@PathVariable("memberNo") Integer memberNo,Principal principal) {
    	Member member = memService.getMemberById(Integer.parseInt(principal.getName()));
        if (member == null) {
            return ResponseEntity.notFound().build();
        }
        List<ProductComment> comments = productCommentService.getCommentsByMember(member);
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

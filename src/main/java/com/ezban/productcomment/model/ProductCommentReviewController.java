package com.ezban.productcomment.model;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ezban.member.model.Member;
import com.ezban.member.model.MemberService;

@RestController
@RequestMapping("/productCommentsReview")
public class ProductCommentReviewController {

    @Autowired
    private ProductCommentReviewService productCommentService;

    @Autowired
    private MemberService memService;

    @GetMapping("/member/{memberId}")
    public ResponseEntity<List<ProductComment>> getCommentsByMember(@PathVariable Integer memberNo) {
        Member member = memService.getMemberById(memberNo);
        if (member == null) {
            return ResponseEntity.notFound().build();
        }
        List<ProductComment> comments = productCommentService.getCommentsByMember(member);
        return ResponseEntity.ok(comments);
    }
}
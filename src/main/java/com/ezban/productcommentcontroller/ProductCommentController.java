package com.ezban.productcommentcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ezban.member.model.Member;
import com.ezban.product.model.Product;
import com.ezban.productcomment.model.ProductComment;
import com.ezban.productcomment.model.ProductCommentDTO;
import com.ezban.productcomment.model.ProductCommentService;

@RestController
@RequestMapping("/api/product/comment")
public class ProductCommentController {

    private final ProductCommentService commentService;

    @Autowired
    public ProductCommentController(ProductCommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping(consumes = "application/json;charset=UTF-8")
    public ResponseEntity<?> addComment(@RequestBody ProductCommentDTO commentDTO, Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Integer memberNo = Integer.parseInt(userDetails.getUsername());

        try {
            ProductComment comment = new ProductComment();
            comment.setProductCommentContent(commentDTO.getCommentContent());
            comment.setProductRate(commentDTO.getProductRate());
            comment.setProductCommentStatus(commentDTO.getProductCommentStatus());
            comment.setProductCommentDate(commentDTO.getProductCommentDate());
            comment.setMember(new Member(memberNo));
            comment.setProduct(new Product(commentDTO.getProductNo()));

            ProductComment savedComment = commentService.save(comment);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedComment);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error saving comment: " + e.getMessage());
        }
    }

    @GetMapping("/{productNo}")
    public ResponseEntity<List<ProductCommentDTO>> getCommentsByProductNo(@PathVariable Integer productNo) {
        try {
            List<ProductCommentDTO> comments = commentService.findCommentsByProductNo(productNo);
            return ResponseEntity.ok(comments);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/stats/{productNo}")
    public ResponseEntity<ProductCommentDTO.CommentStatsDTO> getCommentStats(@PathVariable Integer productNo) {
        try {
            ProductCommentDTO.CommentStatsDTO stats = commentService.getCommentStats(productNo);
            return ResponseEntity.ok(stats);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}

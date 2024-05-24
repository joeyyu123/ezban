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
    public ResponseEntity<?> addComment(@RequestBody ProductCommentDTO commentDTO) {
        System.out.println("Received POST request to add comment");
        try {
            // 將DTO轉換為實體
            ProductComment comment = new ProductComment();
            comment.setProductCommentContent(commentDTO.getCommentContent());
            comment.setProductRate(commentDTO.getProductRate());
            comment.setProductCommentStatus(commentDTO.getProductCommentStatus());
            comment.setProductCommentDate(commentDTO.getProductCommentDate());
            comment.setMember(new Member(commentDTO.getMemberNo())); // 使用新的構造函數
            comment.setProduct(new Product(commentDTO.getProductNo())); // 使用新的構造函數

            ProductComment savedComment = commentService.save(comment);
            // 返回DTO對象
            ProductCommentDTO savedCommentDTO = new ProductCommentDTO(
                savedComment.getProductCommentNo(),
                savedComment.getMember().getMemberNo(),
                savedComment.getProduct().getProductNo(),
                savedComment.getProductCommentContent(),
                savedComment.getProductRate(),
                savedComment.getProductCommentStatus(),
                savedComment.getProductCommentDate() // 添加 productCommentDate 字段
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(savedCommentDTO);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error saving comment: " + e.getMessage());
        }
    }

    @GetMapping("/{productNo}")
    public ResponseEntity<List<ProductCommentDTO>> getCommentsByProductNo(@PathVariable Integer productNo) {
        System.out.println("Fetching comments for product number: " + productNo);
        try {
            List<ProductCommentDTO> comments = commentService.findCommentsByProductNo(productNo);
            System.out.println("Comments fetched: " + comments);
            return ResponseEntity.ok(comments);
        } catch (Exception e) {
            System.out.println("Error fetching comments: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/stats/{productNo}")
    public ResponseEntity<ProductCommentDTO.CommentStatsDTO> getCommentStats(@PathVariable Integer productNo) {
        System.out.println("Fetching comment stats for product number: " + productNo);
        try {
            ProductCommentDTO.CommentStatsDTO stats = commentService.getCommentStats(productNo);
            System.out.println("Comment stats fetched: " + stats);
            return ResponseEntity.ok(stats);
        } catch (Exception e) {
            System.out.println("Error fetching comment stats: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}

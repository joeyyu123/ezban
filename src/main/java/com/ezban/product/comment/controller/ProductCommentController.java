package com.ezban.product.comment.controller;



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

import com.ezban.productcomment.model.ProductComment;
import com.ezban.productcomment.model.ProductCommentDTO;
import com.ezban.productcomment.model.ProductCommentDTO.CommentStatsDTO;
import com.ezban.productcomment.model.ProductCommentService;

@RestController
@RequestMapping("/api/product/comment")
public class ProductCommentController {

    private final ProductCommentService commentService;

    @Autowired
    public ProductCommentController(ProductCommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    public ResponseEntity<ProductComment> addComment(@RequestBody ProductComment comment) {
        try {
            ProductComment savedComment = commentService.save(comment);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedComment);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
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
    public ResponseEntity<CommentStatsDTO> getCommentStats(@PathVariable Integer productNo) {
        try {
            double averageRating = commentService.getAverageRating(productNo);
            long ratingCount = commentService.getRatingCount(productNo);
            CommentStatsDTO stats = new CommentStatsDTO(averageRating, ratingCount);
            return ResponseEntity.ok(stats);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}

package com.ezban.productcomment.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ezban.productcomment.model.ProductComment;
import com.ezban.productcomment.model.ProductCommentService;

@RestController
@RequestMapping("/api/comments")
public class ProductCommentController {

    private static final Logger logger = LoggerFactory.getLogger(ProductCommentController.class);
    private final ProductCommentService commentService;

    @Autowired
    public ProductCommentController(ProductCommentService commentService) {
        this.commentService = commentService;
    }

    // POST方法用於添加新評論
    @PostMapping
    public ResponseEntity<ProductComment> addComment(@RequestBody ProductComment comment) {
        try {
            ProductComment savedComment = commentService.save(comment);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedComment);
        } catch (Exception e) {
            logger.error("Error saving comment", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // GET方法用於獲取所有評論
    @GetMapping
    public ResponseEntity<List<ProductComment>> getAllComments() {
        try {
            List<ProductComment> comments = commentService.findAll();
            return ResponseEntity.ok(comments);
        } catch (Exception e) {
            logger.error("Failed to retrieve comments", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
